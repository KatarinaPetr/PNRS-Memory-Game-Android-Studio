import { Module } from '@nestjs/common';
import { ScoreController } from './score.controller';
import { ScoreService } from './score.service';
import { MongooseModule } from '@nestjs/mongoose';
import { Score, ScoreEntity } from './score.entity';

@Module({
  imports: [
    MongooseModule.forFeature([{ name: Score.name, schema: ScoreEntity }]),
  ],
  controllers: [ScoreController],
  providers: [ScoreService],
})
export class ScoreModule {}
