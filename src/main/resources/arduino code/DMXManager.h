#ifndef DMXMANAGER_H
#define DMXMANAGER_H

#include "dmx.h"
#include "Scene.h"


class DmxManager {

private:

public:
  DmxManager() {
    DMX::Initialize(output);
  }


  void playRandomValues() {
    for (int j = 0; j < 5; j++) {
      int r;
      for (int i = 0; i < 10; i++) {
        r = random(255);
        DMX::Write(i, r);
        delay(200);
      }
      delay(1000);
    }
  }
};

#endif