import { Prop, Schema, SchemaFactory } from '@nestjs/mongoose';
import { Document } from 'mongoose';

export type ScoreDocument = Score & Document;

@Schema()
export class Score {
  @Prop()
  score_id: string;

  @Prop()
  username: string;

  @Prop()
  score: number;
}

export const ScoreEntity = SchemaFactory.createForClass(Score);
