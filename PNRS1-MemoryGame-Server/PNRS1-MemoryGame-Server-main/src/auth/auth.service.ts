import { BadRequestException, Injectable } from '@nestjs/common';
import { UserLoginDto, UserRegisterDto } from './dto/user.dto';
import * as bcrypt from 'bcrypt';
import { User, UserDocument } from './user.entity';
import { InjectModel } from '@nestjs/mongoose';
import { Model } from 'mongoose';

@Injectable()
export class AuthService {
  constructor(@InjectModel(User.name) private userModel: Model<UserDocument>) {}

  async getUserByUsername(username: string): Promise<User> {
    return this.userModel.findOne({ username: username });
  }

  async signUp(userRegisterDto: UserRegisterDto): Promise<void> {
    const { username, password, email } = userRegisterDto;
    if (username == null || username == '') {
      throw new BadRequestException('Please enter username');
    }
    if (password == null || password == '') {
      throw new BadRequestException('Please enter password');
    }
    if (email == null || email == '') {
      throw new BadRequestException('Please enter email');
    }

    const existingUser = await this.getUserByUsername(username);
    if (!existingUser) {
      const salt = await bcrypt.genSalt();
      const hashedPassword = await bcrypt.hash(password, salt);

      try {
        const user = new this.userModel({
          username,
          password: hashedPassword,
          email,
        });
        await user.save();
      } catch (error) {
        throw new BadRequestException(error);
      }
    } else {
      throw new BadRequestException('User already exists');
    }
  }

  async signIn(userLoginDto: UserLoginDto): Promise<void> {
    const { username, password } = userLoginDto;
    if (username == null || username == '') {
      throw new BadRequestException('Please enter username');
    }
    if (password == null || password == '') {
      throw new BadRequestException('Please enter password');
    }
    const user = await this.getUserByUsername(username);
    if (!(user && (await bcrypt.compare(password, user.password)))) {
      throw new BadRequestException('Wrong credentials');
    }
  }
}
