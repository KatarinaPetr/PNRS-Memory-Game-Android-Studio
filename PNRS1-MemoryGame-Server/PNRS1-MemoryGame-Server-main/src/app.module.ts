import { Module } from '@nestjs/common';
import { AuthModule } from './auth/auth.module';
import { ScoreModule } from './score/score.module';
import { MongooseModule } from '@nestjs/mongoose';

@Module({
  imports: [
    AuthModule,
    ScoreModule,
    MongooseModule.forRoot(
      'mongodb+srv://stefan001:9g7fxHfxZz0m7QIg@cluster0.nu0sv.mongodb.net/test\n',
    ),
  ],
})
export class AppModule {}
