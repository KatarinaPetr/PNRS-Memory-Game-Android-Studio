//
// Created by kacul on 6/14/2022.
//
#include "katarina_petrovic_memorygame_JNIexample.h"

JNIEXPORT jdouble JNICALL Java_katarina_petrovic_memorygame_JNIexample_racunajProcenat
  (JNIEnv *env, jobject jobj, jdouble score){
        double x = 100*score/40;
        return x;
  }