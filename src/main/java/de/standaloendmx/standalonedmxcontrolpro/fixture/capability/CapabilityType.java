package de.standaloendmx.standalonedmxcontrolpro.fixture.capability;

public enum CapabilityType {

    NO_FUNCTION("NoFunction"), SHUTTER_STROBE("ShutterStrobe"), STROBE_SPEED("StrobeSpeed"), STROBE_DURATION("StrobeDuration"),
    INTENSITY("Intensity"), COLOR_INTENSITY("ColorIntensity"), COLOR_PRESENT("ColorPreset"), COLOR_TEMPERATURE("ColorTemperature"), PAN("Pan"),
    PAN_CONTINUOUS("PanContinuous"), TILT("Tilt"), TILT_CONTINUOUS("TiltContinuous"), PAN_TILT_SPEED("PanTiltSpeed"), WHEEL_SLOT("WheelSlot"),
    WHEEL_SHAKE("WheelShake"), WHEEL_SLOT_ROTATION("WheelSlotRotation"), WHEEL_ROTATION("WheelRotation"), EFFECT("Effect"), EFFECT_SPEED("EffectSpeed"),
    EFFECT_DURATION("EffectDuration"), EFFECT_PARAMETER("EffectParameter"), SOUND_SENSITIVITY("SoundSensitivity"), BEAM_ANGLE("BeamAngle"),
    BEAM_POSITION("BeamPosition"), FOCUS("Focus"), ZOOM("Zoom"), IRIS("Iris"), IRIS_EFFECT("IrisEffect"), FROST("Frost"), FROST_EFFECT("FrostEffect"),
    PRISM("Prism"), PRISM_ROTATION("PrismRotation"), BLADE_INSERTION("BladeInsertion"), BLASE_ROTATION("BladeRotation"), BLADE_SYSTEM_ROTATION("BladeSystemRotation"),
    FOG("Fog"), FOG_OUTPUT("FogOutput"), FOG_TYPE("FogType"), ROTATION("Rotation"), SPEED("Speed"), TIME("Time"), MAINTENANCE("Maintenance"), GENERIC("Generic");

    private String name;

    CapabilityType(String name) {
        this.name = name;
    }

    public static CapabilityType getByName(String name) {
        for (CapabilityType value : values()) {
            if (value.name.equals(name)) return value;
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
