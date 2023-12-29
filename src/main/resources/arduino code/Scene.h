#include<iostream>
#include<vector>
#include <map>

class TableStep {
private:
    int pos;
    int fadeTime;
    int holdTime;
    std::map<int, int> mapValues;

public:
    TableStep(int pos, int fadeTime, int holdTime)
        : pos(pos), fadeTime(fadeTime), holdTime(holdTime)
    {
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

    std::map<int, int> getMapValues() {
        return mapValues;
    }
};

struct GroupColor {
  int r;
  int g;
  int b;
};

class MyScene {
private:
    int sceneId;
    std::string name;
    std::string time;
    std::string repeat;

    GroupColor color;
    std::vector<TableStep> steps;

public:
    MyScene(GroupColor color) : color(color) {
        // implementation omitted for shortness
    }

    std::vector<TableStep> getSteps() {
        return steps;
    }

    void initialize() {

    }
};