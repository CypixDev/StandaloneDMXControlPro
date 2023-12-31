
class TableStep {
private:
  int pos;
  int fadeTime;
  int holdTime;
  byte channelValues[20];

public:
  TableStep() {}
  TableStep(int pos, int fadeTime, int holdTime, byte inputChannelValues[])
    : pos(pos), fadeTime(fadeTime), holdTime(holdTime) {
    for (int i = 0; i < sizeof(channelValues); ++i) {
      channelValues[i] = inputChannelValues[i];
    }
  }

  int getPos() {
    return pos;
  }

  int getFadeTime() {
    return fadeTime;
  }

  int getHoldTime() {
    return holdTime;
  }

  byte* getChannelValues() {
    return channelValues;
  }
};

struct GroupColor {
  int r;
  int g;
  int b;

  GroupColor(int r, int g, int b) : r(r), g(g), b(b) {}
};

class MyScene {
private:
    String sceneUUID;
    String name;
    int time;

    GroupColor color;
    TableStep* steps;

public:
    int numberOfSteps;

    MyScene(String sceneUUID, String name, int time, GroupColor color, TableStep* inputSteps, int numSteps)
    : sceneUUID(sceneUUID), name(name), time(time), color(color), steps(inputSteps), numberOfSteps(numSteps)
    {
    }

    TableStep* getSteps() {
        return steps;
    }

    int getNumberOfSteps() {
        return numberOfSteps;
    }

    void initialize() {
        // Ihre Initialisierungslogik hier...
    }
};