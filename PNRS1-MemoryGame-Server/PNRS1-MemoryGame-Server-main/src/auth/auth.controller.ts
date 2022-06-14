import { Body, Controller, Post } from '@nestjs/common';
import { AuthService } from './auth.service';
import { UserLoginDto, UserRegisterDto } from './dto/user.dto';

@Controller('auth')
export class AuthController {
  constructor(private readonly authService: AuthService) {}

  @Post('/signup')
  async signUp(@Body() userRegisterDto: UserRegisterDto): Promise<void> {
    return await this.authService.signUp(userRegisterDto);
    //METHOD POST
    //DESC Method that saves a new user
    //API http://localhost:3000/auth/signup
    //NO RESPONSE
  }

  @Post('/signin')
  async signIn(@Body() userLoginDto: UserLoginDto): Promise<void> {
    return await this.authService.signIn(userLoginDto);
    //METHOD POST
    //DESC Method that check user credentials
    //API http://localhost:3000/auth/signin
    //NO RESPONSE
  }
}
