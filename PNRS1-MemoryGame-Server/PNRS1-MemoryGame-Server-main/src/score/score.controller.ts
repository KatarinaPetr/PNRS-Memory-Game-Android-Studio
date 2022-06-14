import { Body, Controller, Delete, Get, Post, Query } from '@nestjs/common';
import { ScoreService } from './score.service';
import { Score } from './score.entity';
import { SaveScoreDto } from './dto/score.dto';

@Controller('score')
export class ScoreController {
  constructor(private readonly scoreService: ScoreService) {}

  @Get()
  async getScoresByUsername(@Query() username: string): Promise<Score[]> {
    if (Object.keys(username).length === 0) {
      return await this.scoreService.getAllScores();
      //METHOD GET
      //DESC Method that returns all games
      //API http://localhost:3000/score/
      //RESPONSE:
      //[
      //    {
      //         "_id": "6282c753522542464c9af0aa",
      //         "username": "stefan20001221",
      //         "score": 36,
      //         "__v": 0
      //    },...
      //]
    }
    return await this.scoreService.getScoresByUsername(username);
    //METHOD GET
    //DESC Method that returns all games played by one user
    //API http://localhost:3000/score/?username=<username>
    //PARAMS: username
    //RESPONSE:
    //[
    //    {
    //         "_id": "6282c753522542464c9af0aa",
    //         "username": "stefan20001221",
    //         "score": 36,
    //         "__v": 0
    //    },...
    //]
  }

  @Post()
  async saveScore(@Body() saveScoreDto: SaveScoreDto): Promise<void> {
    return await this.scoreService.saveScore(saveScoreDto);
    //METHOD POST
    //DESC Method that saves a new game
    //API http://localhost:3000/score/
    //NO RESPONSE
  }

  @Delete()
  async deleteUserScores(@Query() username: string): Promise<void> {
    return await this.scoreService.deleteUserScores(username);
    //METHOD DELETE
    //DESC Method that deletes all games played by one user
    //API http://localhost:3000/score/
    //NO RESPONSE
  }
}
