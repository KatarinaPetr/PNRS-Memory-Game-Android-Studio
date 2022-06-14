import { IsNotEmpty, IsNumberString, IsString } from 'class-validator';

export class SaveScoreDto {
  @IsNotEmpty()
  @IsString()
  username: string;

  @IsNotEmpty()
  @IsNumberString()
  score: string;
}
