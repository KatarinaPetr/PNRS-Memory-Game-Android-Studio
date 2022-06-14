import { BadRequestException, Injectable } from '@nestjs/common';
import { InjectModel } from '@nestjs/mongoose';
import { Model } from 'mongoose';
import { Score, ScoreDocument } from './score.entity';
import { SaveScoreDto } from './dto/score.dto';

@Injectable()
export class ScoreService {
  constructor(
    @InjectModel(Score.name) private scoreModel: Model<ScoreDocument>,
  ) {}

  async getAllScores(): Promise<Score[]> {
    return await this.scoreModel.find().exec();
  }

  async getScoresByUsername(username: string): Promise<Score[]> {
    return this.scoreModel.find().where(username);
  }

  async saveScore(saveScoreDto: SaveScoreDto): Promise<void> {
    const { username, score } = saveScoreDto;
    if (username == null || username == '') {
      throw new BadRequestException('Please enter username');
    }
    if (score == null || score == '') {
      throw new BadRequestException('Please enter score');
    }
    try {
      const savedScore = new this.scoreModel({
        username,
        score,
      });
      await savedScore.save();
    } catch (error) {
      throw new BadRequestException(error);
    }
  }

  async deleteUserScores(username: string): Promise<void> {
    const scoresExists = await this.getScoresByUsername(username);
    if (scoresExists) {
      try {
        await this.scoreModel.deleteMany().where(username);
      } catch (error) {
        throw new BadRequestException(error);
      }
    } else {
      throw new BadRequestException(`Games for user ${username} are not found`);
    }
  }
}
