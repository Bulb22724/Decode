package org.firstinspires.ftc.teamcode.modules;

import static java.lang.Math.PI;

import android.annotation.SuppressLint;

import com.qualcomm.robotcore.hardware.HardwareDeviceHealth;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import com.qualcomm.robotcore.hardware.configuration.annotations.I2cDeviceType;
import com.qualcomm.robotcore.util.TypeConversion;

import java.util.function.Supplier;

@I2cDeviceType
@DeviceProperties(name = "Магнитный энкодер AMS AS5600", xmlTag = "AS5600")
@SuppressLint("WrongCommentType")
public class AS5600 extends I2cDeviceSynchDevice<I2cDeviceSynch> {
    private static class Button {
        // понимаю, что здесь должно быть "flag", но так легче портировать
        public boolean falg_button_state = false;
        public byte minimum_agc = 0;
        public byte maximum_agc = 0;
        public byte deviation = 0;

        public Button(boolean falg_button_state, byte minimum_agc, byte maximum_agc, byte deviation) {
            this.falg_button_state = falg_button_state;
            this.minimum_agc = minimum_agc;
            this.maximum_agc = maximum_agc;
            this.deviation = deviation;
        }
    }

    /* Burn Commands */
    public static final byte AS5600_BURN_REG = (byte)0xFF;
    // Команды регистра BURN
    public static final byte AS5600_CMD_BURN_ANGLE = (byte)0x80;
    public static final byte AS5600_CMD_BURN_SETTINGS = 0x40;
    // Option A: Angle Programming Through the I²C Interface (Step 7)
    // Option C: Programming a Maximum Angular Range Through the I²C Interface (Step 4)
    public static final byte AS5600_CMD_BURN_LOAD_OTP_CONTENT_0 = 0x01;
    public static final byte AS5600_CMD_BURN_LOAD_OTP_CONTENT_1 = 0x11;
    public static final byte AS5600_CMD_BURN_LOAD_OTP_CONTENT_2 = 0x10;

        /*=== Вспомогательные значения ===*/
    // Предельное значение регистра CONF_ZMCO
    public static final byte AS5600_MAX_VALUE_ZMCO = 0x03;
    // Минимальный угол 18 градусов, примерно 205
    public static final byte AS5600_MIN_ANGLE_VALUE_DEC = (byte)205; // 4096 /360 * 18 = 204.8
    // Ответы стандартного вида успех/ошибка
    public static final byte AS5600_DEFAULT_REPORT_ERROR = 0;
    public static final byte AS5600_DEFAULT_REPORT_OK    = 1;
    // Состояние сторожевого таймера
    public static final byte AS5600_WATCHDOG_OFF = 0;
    public static final byte AS5600_WATCHDOG_ON  = 1;
    public enum Register {
        AS5600_CONFIG_REG_ZMCO  (0x00),
        AS5600_CONFIG_REG_ZPOS_H(0x01),
        AS5600_CONFIG_REG_ZPOS_L(0x02),
        AS5600_CONFIG_REG_MPOS_H(0x03),
        AS5600_CONFIG_REG_MPOS_L(0x04),
        AS5600_CONFIG_REG_MANG_H(0x05),
        AS5600_CONFIG_REG_MANG_L(0x06),
        AS5600_CONFIG_REG_CONF_H(0x07),
        AS5600_CONFIG_REG_CONF_L(0x08),

        /* Output Registers */
        AS5600_OUT_REG_RAW_ANGLE_H    (0x0C),
        AS5600_OUT_REG_RAW_ANGLE_L    (0x0D),
        AS5600_OUT_REG_ANGLE_H        (0x0E),
        AS5600_OUT_REG_ANGLE_L        (0x0F),
        /* Status Registers */
        AS5600_STATUS_REG             (0x0B),
        AS5600_STATUS_REG_AGC         (0x1A),
        AS5600_STATUS_REG_MAGNITUDE_H (0x1B),
        AS5600_STATUS_REG_MAGNITUDE_L (0x1C),

        /* Burn Commands */
        AS5600_BURN_REG (0xFF)

        ;

        public final byte bVal;
        Register(int bVal) {
            this.bVal = (byte)bVal;
        }
    }

    public enum Command {
        // Команды регистра BURN
        AS5600_CMD_BURN_ANGLE      (0x80),
        AS5600_CMD_BURN_SETTINGS   (0x40),
        // Option A: Angle Programming Through the I²C Interface (Step 7)
        // Option C: Programming a Maximum Angular Range Through the I²C Interface (Step 4)
        AS5600_CMD_BURN_LOAD_OTP_CONTENT_0 (0x01),
        AS5600_CMD_BURN_LOAD_OTP_CONTENT_1 (0x11),
        AS5600_CMD_BURN_LOAD_OTP_CONTENT_2 (0x10),
        ;
        public final byte bVal;
        Command(int value) {
            bVal = (byte)value;
        }
    }

    public enum AS5600PowerModes {
        AS5600_NOM_POWER_MODE,
        AS5600_LOW_POWER_MODE_1,
        AS5600_LOW_POWER_MODE_2,
        AS5600_LOW_POWER_MODE_3,
    };

    // Режимы гистерезиса
    public enum AS5600Hysteresis {
        AS5600_HYSTERESIS_OFF,
        AS5600_HYSTERESIS_1_LSB,
        AS5600_HYSTERESIS_2_LSB,
        AS5600_HYSTERESIS_3_LSB,
    };
    // Режимы вывода OUT
    public enum AS5600OutputStage {
        AS5600_OUTPUT_ANALOG_FULL_RANGE,
        AS5600_OUTPUT_ANALOG_REDUCED_RANGE,
        AS5600_OUTPUT_DIGITAL_PWM,
    };
    // Варианты частоты ШИМ
    public enum AS5600PWMFrequency {
        AS5600_PWM_FREQUENCY_115HZ,
        AS5600_PWM_FREQUENCY_230HZ,
        AS5600_PWM_FREQUENCY_460HZ,
        AS5600_PWM_FREQUENCY_920HZ,
    };
    // Шаги медленной фильтрации
    public enum AS5600SlowFilter {
        AS5600_SLOW_FILTER_16X,
        AS5600_SLOW_FILTER_8X,
        AS5600_SLOW_FILTER_4X,
        AS5600_SLOW_FILTER_2X,
    };
    // Пороги быстрой фильтрации
    public enum AS5600FastFilterThreshold {
        AS5600_FAST_FILTER_THRESHOLD_SLOW_FILTER_ONLY,
        AS5600_FAST_FILTER_THRESHOLD_6_LSB,
        AS5600_FAST_FILTER_THRESHOLD_7_LSB,
        AS5600_FAST_FILTER_THRESHOLD_9_LSB,
        AS5600_FAST_FILTER_THRESHOLD_18_LSB,
        AS5600_FAST_FILTER_THRESHOLD_21_LSB,
        AS5600_FAST_FILTER_THRESHOLD_24_LSB,
        AS5600_FAST_FILTER_THRESHOLD_10_LSB,
    };
    // Положительное направление вращения
    public enum AS5600DirectionPolarity {
        AS5600_DIRECTION_POLARITY_CLOCKWISE, // По часовой стрелке (LOW - 0)
        AS5600_DIRECTION_POLARITY_COUNTERCLOCKWISE, // Против часовй стрелки (HIGH - 1)
    };
    // Расшифровка результата метода getStatus
    public enum AS5600StatusReports {
        AS5600_STATUS_REPORT_MD0_ML0_MH_0(1),
        AS5600_STATUS_REPORT_MD0_ML1_MH_0 (2),
        AS5600_STATUS_REPORT_MD1_ML0_MH_0 (4),
        AS5600_STATUS_REPORT_MD1_ML0_MH_1 (5),
        AS5600_STATUS_REPORT_MD1_ML1_MH_0 (6),

        ;
        public final byte bVal;
        AS5600StatusReports(int value) {
            bVal = (byte)value;
        }

        public static AS5600StatusReports fromInt(int value) {
            return AS5600StatusReports.values()[value - 1];
        }
    };
    // Флаги для использования с методами из Burn Commands
    public enum AS5600SpecialVerifyFlags {
        AS5600_FLAG_SPECIAL_VERIFY_DISABLE,
        AS5600_FLAG_SPECIAL_VERIFY_ENABLE,
    };
    // Ответы методов burnZeroAndMaxPositions, burnMaxAngleAndConfigurationValue
    public enum AS5600BurnReports {
        // по идее должно быть "report" но тогда нужно везде менять
        AS5600_BURN_REPROT_SENSOR_NOT_CONNECTED,
        AS5600_BURN_REPROT_MAGNET_NOT_FOUND,
        AS5600_BURN_REPROT_WRITE_OK,
        AS5600_BURN_REPROT_WRITE_WRONG,
        AS5600_BURN_REPROT_WRITE_OK_WITHOUT_VERIFY,
        AS5600_BURN_REPROT_ZPOS_MPOS_NOT_SET,
        AS5600_BURN_REPROT_ATTEMPTS_ENDED,
        AS5600_BURN_REPROT_ANGLE_VALUE_TOO_SMALL,
        AS5600_BURN_REPROT_WRITE_OK_WITHOUT_MAXANGLE,
        AS5600_BURN_REPROT_WRITE_OK_WITHOUT_VERIFY_WITHOUT_MAXANGLE,
    };

    public enum AS5600ConfLowRegisterBits {
        AS5600_CONF_BIT_PM_0,
        AS5600_CONF_BIT_PM_1,
        AS5600_CONF_BIT_HYST_0,
        AS5600_CONF_BIT_HYST_1,
        AS5600_CONF_BIT_OUTS_0,
        AS5600_CONF_BIT_OUTS_1,
        AS5600_CONF_BIT_PWMF_0,
        AS5600_CONF_BIT_PWMF_1,
    };

    enum AS5600ConfHighRegisterBits {
        AS5600_CONF_BIT_SF_0,
        AS5600_CONF_BIT_SF_1,
        AS5600_CONF_BIT_FTH_0,
        AS5600_CONF_BIT_FTH_1,
        AS5600_CONF_BIT_FTH_2,
        AS5600_CONF_BIT_WD,
    };

    public enum AS5600StatusRegisterBits {
        AS5600_STATUS_BIT_MH_3(3),
        AS5600_STATUS_BIT_ML_4(4),
        AS5600_STATUS_BIT_MD_5(5);

        public final byte bVal;
        AS5600StatusRegisterBits(int bVal) {
            this.bVal = (byte)bVal;
        }
    };

    public static final I2cAddr ADDRESS = new I2cAddr(0x36);

    private Button _virtual_button_;

    @Override
    public Manufacturer getManufacturer() {
        return Manufacturer.AMS;
    }

    @Override
    protected synchronized boolean doInitialize()
    {
        return true;
    }

    @Override
    public String getDeviceName()
    {
        return "Магнитный энкодер AMS AS5600";
    }

    protected void setOptimalReadWindow()
    {
        // Sensor registers are read repeatedly and stored in a register. This method specifies the
        // registers and repeat read mode
        I2cDeviceSynch.ReadWindow readWindow = new I2cDeviceSynch.ReadWindow(
                Register.AS5600_CONFIG_REG_ZMCO.bVal,
                16,
                I2cDeviceSynch.ReadMode.REPEAT);
        this.deviceClient.setReadWindow(readWindow);
    }

    protected void writeShort(final Register reg, short value)
    {
        deviceClient.write(reg.bVal, TypeConversion.shortToByteArray(value));
    }

    protected short readShort(Register reg)
    {
        return TypeConversion.byteArrayToShort(deviceClient.read(reg.bVal, 2));
    }

    protected byte readByte(Register reg) {
        return deviceClient.read(reg.bVal, 1)[0];
    }

    protected void writeByte(Register reg) {deviceClient.write8(reg.bVal);}

    protected void SendFirstRegister(Register reg) {
        writeByte(reg);
    }

    protected byte RequestSingleRegister() {
        return deviceClient.read8();
    }

    protected short RequestPairRegisters() {
        return TypeConversion.byteArrayToShort(new byte[] {
                RequestSingleRegister(), RequestSingleRegister()});
    }

    protected void WriteOneByte(Register reg, byte payload) {
        deviceClient.write8(reg.bVal, payload);
    }

    protected void WriteTwoBytes(Register lowReg, Register highReg, short payload) {
        byte[] arr = TypeConversion.shortToByteArray(payload);

        // Хз, ардуино вроде little-endian, shortToByteArray использует big endian.
        // Если не будет работать, меняйте местами индексы
        deviceClient.write8(lowReg.bVal, arr[0]);
        deviceClient.write8(highReg.bVal, arr[1]);
    }

    public void SetClock(int freq) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("API I2C не поддерживает изменение частоты");
    }

    public void loadSavedValues() {
        deviceClient.write8(
                Register.AS5600_BURN_REG.bVal,
                Command.AS5600_CMD_BURN_LOAD_OTP_CONTENT_0.bVal
        );
        deviceClient.write8(
                Register.AS5600_BURN_REG.bVal,
                Command.AS5600_CMD_BURN_LOAD_OTP_CONTENT_1.bVal
        );
        deviceClient.write8(
                Register.AS5600_BURN_REG.bVal,
                Command.AS5600_CMD_BURN_LOAD_OTP_CONTENT_2.bVal
        );
    }

    public boolean isConnected() {
        return deviceClient.getHealthStatus() == HardwareDeviceHealth.HealthStatus.HEALTHY;
    }

    public void setButtonMinAGC(byte _btn_min_agc) {
        _virtual_button_.minimum_agc = _btn_min_agc;
    }

    public byte getButtonMinAGC() {
        return _virtual_button_.minimum_agc;
    }

    public void setButtonMaxAGC(byte _btn_max_agc) {
        _virtual_button_.maximum_agc = _btn_max_agc;
    }

    public byte getButtonMaxAGC() {
        return _virtual_button_.maximum_agc;
    }

    public void setButtonDeviation(byte _btn_div) {
        _virtual_button_.deviation = _btn_div;
    }

    public byte getButtonDeviation() {
        return _virtual_button_.deviation;
    }

    public boolean isButtonPressed() {
        byte agc_value = getAutomaticGainControl();
        if (!_virtual_button_.falg_button_state && (agc_value < (_virtual_button_.minimum_agc + _virtual_button_.deviation))) {
            _virtual_button_.falg_button_state = true;
            return true;
        } else {
            return false;
        }
    }

    byte getBurnPositionsCount() {
        SendFirstRegister(Register.AS5600_CONFIG_REG_ZMCO);
        return RequestSingleRegister();
    }

    public short getZeroPosition() {
        SendFirstRegister(Register.AS5600_CONFIG_REG_ZPOS_H);
        return RequestPairRegisters();
    }

    public void setZeroPosition(short _zero_position) {
        WriteTwoBytes(Register.AS5600_CONFIG_REG_ZPOS_L, Register.AS5600_CONFIG_REG_ZPOS_H,
                _zero_position);
    }

    public boolean setZeroPositionVerify(short _zero_position) {
        setZeroPosition(_zero_position);
        return (getZeroPosition() == _zero_position);
    }

    public void setZeroPositionViaRawAngle() {
        short raw_angle = (short) getRawAngle();
        WriteTwoBytes(Register.AS5600_CONFIG_REG_ZPOS_L, Register.AS5600_CONFIG_REG_ZPOS_H,
                raw_angle);
    }

    public boolean setZeroPositionViaRawAngleVerify() {
        short raw_angle = (short) getRawAngle();
        WriteTwoBytes(Register.AS5600_CONFIG_REG_ZPOS_L, Register.AS5600_CONFIG_REG_ZPOS_H, raw_angle);
        return getZeroPosition() == raw_angle;
    }

    public short getMaxPosition() {
        SendFirstRegister(Register.AS5600_CONFIG_REG_MPOS_H);
        return RequestPairRegisters();
    }

    void setMaxPosition(short _max_position) {
        WriteTwoBytes(Register.AS5600_CONFIG_REG_MPOS_L, Register.AS5600_CONFIG_REG_MPOS_H,
                _max_position);
    }

    boolean setMaxPositionVerify(short _max_position) {
        setMaxPosition(_max_position);
        return (getMaxPosition() == _max_position);
    }

    public void setMaxPositionViaRawAngle() {
        short raw_angle = (short) getRawAngle();
        WriteTwoBytes(Register.AS5600_CONFIG_REG_MPOS_L, Register.AS5600_CONFIG_REG_MPOS_H,
                raw_angle);
    }

    public boolean setMaxPositionViaRawAngleVerify() {
        short raw_angle = (short) getRawAngle();
        WriteTwoBytes(Register.AS5600_CONFIG_REG_MPOS_L, Register.AS5600_CONFIG_REG_MPOS_H,
                raw_angle);
        return (getMaxPosition() == raw_angle);
    }

    public short getMaxAngle() {
        SendFirstRegister(Register.AS5600_CONFIG_REG_MANG_H);
        return RequestPairRegisters();
    }

    public void setMaxAngle(short _max_angle) {
        WriteTwoBytes(Register.AS5600_CONFIG_REG_MANG_L, Register.AS5600_CONFIG_REG_MANG_H,
                _max_angle);
    }

    public boolean setMaxAngleVerify(short _max_angle) {
        setMaxAngle(_max_angle);
        return (getMaxAngle() == _max_angle);
    }

    public void setMaxAngleViaRawAngle() {
        short raw_angle = (short) getRawAngle();
        WriteTwoBytes(Register.AS5600_CONFIG_REG_MANG_L, Register.AS5600_CONFIG_REG_MANG_H, raw_angle);
    }

    public boolean setMaxAngleViaRawAngleVerify() {
        short raw_angle = (short) getRawAngle();
        WriteTwoBytes(Register.AS5600_CONFIG_REG_MANG_L, Register.AS5600_CONFIG_REG_MANG_H,
                raw_angle);
        return (getMaxAngle() == raw_angle);
    }

    public short getRawConfigurationValue() {
        SendFirstRegister(Register.AS5600_CONFIG_REG_CONF_H);
        return RequestPairRegisters();
    }

    public void setRawConfigurationValue(short _confuration_value) {
        WriteTwoBytes(Register.AS5600_CONFIG_REG_CONF_L, Register.AS5600_CONFIG_REG_CONF_H,
                _confuration_value);
    }

    public boolean setRawConfigurationValueVerify(short _confuration_value) {
        setRawConfigurationValue(_confuration_value);
        return (getRawConfigurationValue() == _confuration_value);
    }

    public AS5600PowerModes getPowerMode() {
        SendFirstRegister(Register.AS5600_CONFIG_REG_CONF_L);
        return (AS5600PowerModes.values()[RequestSingleRegister() & 0x03]); // 0x03=0b00000011
    }

    public void setPowerMode(AS5600PowerModes _power_mode) {
        SendFirstRegister(Register.AS5600_CONFIG_REG_CONF_L);
        byte conf_l_raw = RequestSingleRegister();
        WriteOneByte(Register.AS5600_CONFIG_REG_CONF_L, conf_l_raw |= (byte) _power_mode.ordinal());
    }

    public boolean setPowerModeVerify(AS5600PowerModes _power_mode) {
        setPowerMode(_power_mode);
        return (getPowerMode() == _power_mode);
    }

    public void enableNomPowerMode() {
        setPowerMode(AS5600PowerModes.AS5600_NOM_POWER_MODE);
    }

    public boolean enableNomPowerModeVerify() {
        return setPowerModeVerify(AS5600PowerModes.AS5600_NOM_POWER_MODE);
    }

    public void enableLowPowerMode1() {
        setPowerMode(AS5600PowerModes.AS5600_LOW_POWER_MODE_1);
    }

    /*
     * @brief: включить режим питания 1 с подтверждением. биты (PM:0,PM:1) регистра CONF(1:0)
     * @return:
     *  AS5600_DEFAULT_REPORT_ERROR - не удалось включить режим
     *  AS5600_DEFAULT_REPORT_OK - режим включиен
     */
    public boolean enableLowPowerMode1Verify() {
        return setPowerModeVerify(AS5600PowerModes.AS5600_LOW_POWER_MODE_1);
    }
    /*
     * @brief: включить режим питания 2. биты (PM:0,PM:1) регистра CONF(1:0)
     */
    public void enableLowPowerMode2() {
        setPowerMode(AS5600PowerModes.AS5600_LOW_POWER_MODE_2);
    }
    /*
     * @brief: включить режим питания 2 с подтверждением. биты (PM:0,PM:1) регистра CONF(1:0)
     * @return:
     *  AS5600_DEFAULT_REPORT_ERROR - не удалось включить режим
     *  AS5600_DEFAULT_REPORT_OK - режим включиен
     */
    public boolean enableLowPowerMode2Verify() {
        return setPowerModeVerify(AS5600PowerModes.AS5600_LOW_POWER_MODE_2);
    }
    /*
     * @brief: включить режим питания 3. биты (PM:0,PM:1) регистра CONF(1:0)
     */
    public void enableLowPowerMode3() {
        setPowerMode(AS5600PowerModes.AS5600_LOW_POWER_MODE_3);
    }
    /*
     * @brief: включить режим питания 3 с подтверждением. биты (PM:0,PM:1) регистра CONF(1:0)
     * @return:
     *  AS5600_DEFAULT_REPORT_ERROR - не удалось включить режим
     *  AS5600_DEFAULT_REPORT_OK - режим включиен
     */
    public boolean enableLowPowerMode3Verify() {
        return setPowerModeVerify(AS5600PowerModes.AS5600_LOW_POWER_MODE_3);
    }

    public AS5600Hysteresis getHysteresis() {
        SendFirstRegister(Register.AS5600_CONFIG_REG_CONF_L);
        return AS5600Hysteresis.values()[((RequestSingleRegister() >> AS5600ConfLowRegisterBits.AS5600_CONF_BIT_HYST_0.ordinal()) & 0x03)]; // 0x03=0b00000011
    }

    public void setHysteresis(AS5600Hysteresis _hysteresis) {
        SendFirstRegister(Register.AS5600_CONFIG_REG_CONF_L);
        byte conf_l_raw = RequestSingleRegister();
        WriteOneByte(Register.AS5600_CONFIG_REG_CONF_L, conf_l_raw |= (byte) (_hysteresis.ordinal() << AS5600ConfLowRegisterBits.AS5600_CONF_BIT_HYST_0.ordinal()));
    }

    public boolean setHysteresisVerify(AS5600Hysteresis _hysteresis) {
        setHysteresis(_hysteresis);
        return (getHysteresis() == _hysteresis);
    }
    /*
     * @brief: выключить гистерезис (HYST-00)
     */
    public void disableHysteresis() {
        setHysteresis(AS5600Hysteresis.AS5600_HYSTERESIS_OFF);
    }

    public boolean disableHysteresisVerify() {
        return setHysteresisVerify(AS5600Hysteresis.AS5600_HYSTERESIS_OFF);
    }
    /*
     * @brief: включить гистерезис на 1 LSB (HYST-01)
     */
    public void enableHysteresis1LSB() {
        setHysteresis(AS5600Hysteresis.AS5600_HYSTERESIS_1_LSB);
    }
    /*
     * @brief: включить гистерезис на 1 LSB (HYST-01) с подтверждением
     * @return:
     *  AS5600_DEFAULT_REPORT_ERROR - не удалось включить
     *  AS5600_DEFAULT_REPORT_OK - удалось включить
     */
    public boolean enableHysteresis1LSBVerify() {
        return setHysteresisVerify(AS5600Hysteresis.AS5600_HYSTERESIS_1_LSB);
    }
    /*
     * @brief: включить гистерезис на 2 LSB (HYST-10)
     */
    public void enableHysteresis2LSB() {
        setHysteresis(AS5600Hysteresis.AS5600_HYSTERESIS_2_LSB);
    }
    /*
     * @brief: включить гистерезис на 2 LSB (HYST-10) с подтверждением
     * @return:
     *  AS5600_DEFAULT_REPORT_ERROR - не удалось включить
     *  AS5600_DEFAULT_REPORT_OK - удалось включить
     */
    public boolean enableHysteresis2LSBVerify() {
        return setHysteresisVerify(AS5600Hysteresis.AS5600_HYSTERESIS_2_LSB);
    }
    /*
     * @brief: включить гистерезис на 3 LSB (HYST-11)
     */
    public void enableHysteresis3LSB() {
        setHysteresis(AS5600Hysteresis.AS5600_HYSTERESIS_3_LSB);
    }
    /*
     * @brief: включить гистерезис на 3 LSB (HYST-11) с подтверждением
     * @return:
     *  AS5600_DEFAULT_REPORT_ERROR - не удалось включить
     *  AS5600_DEFAULT_REPORT_OK - удалось включить
     */
    public boolean enableHysteresis3LSBVerify() {
        return setHysteresisVerify(AS5600Hysteresis.AS5600_HYSTERESIS_3_LSB);
    }

    /*
     * @brief: получить режим работы контакта OUT
     * @return:
     *  AS5600_OUTPUT_ANALOG_FULL_RANGE
     *  AS5600_OUTPUT_ANALOG_REDUCED_RANGE
     *  AS5600_OUTPUT_DIGITAL_PWM
     */
    public AS5600OutputStage getOutputStage() {
        SendFirstRegister(Register.AS5600_CONFIG_REG_CONF_L);
        return AS5600OutputStage.values()[((RequestSingleRegister() >> AS5600ConfLowRegisterBits.AS5600_CONF_BIT_OUTS_0.ordinal()) & 0x03)]; // 0x03=0b00000011
    }

    public void setOutputStage(AS5600OutputStage _output_stage) {
        SendFirstRegister(Register.AS5600_CONFIG_REG_CONF_L);
        byte conf_l_raw = RequestSingleRegister();
        WriteOneByte(Register.AS5600_CONFIG_REG_CONF_L, conf_l_raw |= (byte) (_output_stage.ordinal() << AS5600ConfLowRegisterBits.AS5600_CONF_BIT_OUTS_0.ordinal()));
    }

    public boolean setOutputStageVerify(AS5600OutputStage _output_stage) {
        setOutputStage(_output_stage);
        return (getOutputStage() == _output_stage);
    }

    public void enableOutputAnalogFullRange() {
        setOutputStage(AS5600OutputStage.AS5600_OUTPUT_ANALOG_FULL_RANGE);
    }
    /*
     * @brief: установить режим работы контакта OUT как аналоговый выход (0-100%) с подтверждением
     * @return:
     *  AS5600_DEFAULT_REPORT_ERROR - не удалось установить
     *  AS5600_DEFAULT_REPORT_OK - удалось установить
     */
    public boolean enableOutputAnalogFullRangeVerify() {
        return setOutputStageVerify(AS5600OutputStage.AS5600_OUTPUT_ANALOG_FULL_RANGE);
    }
    /*
     * @brief: установить режим работы контакта OUT как аналоговый выход (10-90%)
     */
    public void enableOutputAnalogReducedRange() {
        setOutputStage(AS5600OutputStage.AS5600_OUTPUT_ANALOG_REDUCED_RANGE);
    }
    /*
     * @brief: установить режим работы контакта OUT как аналоговый выход (10-90%) с подтверждением
     * @return:
     *  AS5600_DEFAULT_REPORT_ERROR - не удалось установить
     *  AS5600_DEFAULT_REPORT_OK - удалось установить
     */
    public boolean enableOutputAnalogReducedRangeVerify() {
        return setOutputStageVerify(AS5600OutputStage.AS5600_OUTPUT_ANALOG_REDUCED_RANGE);
    }
    /*
     * @brief: установить режим работы контакта OUT как цифровой ШИМ выход
     */
    public void enableOutputDigitalPWM() {
        setOutputStage(AS5600OutputStage.AS5600_OUTPUT_DIGITAL_PWM);
    }
    /*
     * @brief: установить режим работы контакта OUT как цифровой ШИМ выход с подтверждением
     * @return:
     *  AS5600_DEFAULT_REPORT_ERROR - не удалось установить
     *  AS5600_DEFAULT_REPORT_OK - удалось установить
     */
    public boolean enableOutputDigitalPWMVerify() {
        return setOutputStageVerify(AS5600OutputStage.AS5600_OUTPUT_DIGITAL_PWM);
    }

    public AS5600PWMFrequency getPWMFrequency() {
        SendFirstRegister(Register.AS5600_CONFIG_REG_CONF_L);
        return AS5600PWMFrequency.values()[((RequestSingleRegister() >> AS5600ConfLowRegisterBits.AS5600_CONF_BIT_PWMF_0.ordinal()) & 0x03)]; // 0x03=0b00000011
    }

    public void setPWMFrequency(AS5600PWMFrequency _pwm_frequency) {
        SendFirstRegister(Register.AS5600_CONFIG_REG_CONF_L);
        byte conf_l_raw = RequestSingleRegister();
        WriteOneByte(Register.AS5600_CONFIG_REG_CONF_L, conf_l_raw |= (byte) (_pwm_frequency.ordinal() << AS5600ConfLowRegisterBits.AS5600_CONF_BIT_PWMF_0.ordinal()));
    }

    public boolean setPWMFrequencyVerify(AS5600PWMFrequency _pwm_frequency) {
        setPWMFrequency(_pwm_frequency);
        return (getPWMFrequency() == _pwm_frequency);
    }

    public Runnable enablePWMFrequency115Hz = () -> setPWMFrequency(AS5600PWMFrequency.AS5600_PWM_FREQUENCY_115HZ);
    public Supplier<Boolean> enablePWMFrequency115HzVerify = () -> setPWMFrequencyVerify(AS5600PWMFrequency.AS5600_PWM_FREQUENCY_115HZ);
    public Runnable enablePWMFrequency230Hz = () -> setPWMFrequency(AS5600PWMFrequency.AS5600_PWM_FREQUENCY_230HZ);
    public Supplier<Boolean> enablePWMFrequency230HzVerify = () -> setPWMFrequencyVerify(AS5600PWMFrequency.AS5600_PWM_FREQUENCY_230HZ);
    public Runnable enablePWMFrequency460Hz = () -> setPWMFrequency(AS5600PWMFrequency.AS5600_PWM_FREQUENCY_460HZ);
    public Supplier<Boolean> enablePWMFrequency460HzVerify = () -> setPWMFrequencyVerify(AS5600PWMFrequency.AS5600_PWM_FREQUENCY_460HZ);
    public Runnable enablePWMFrequency920Hz = () -> setPWMFrequency(AS5600PWMFrequency.AS5600_PWM_FREQUENCY_920HZ);
    public Supplier<Boolean> enablePWMFrequency920HzVerify = () -> setPWMFrequencyVerify(AS5600PWMFrequency.AS5600_PWM_FREQUENCY_920HZ);

    public AS5600SlowFilter getSlowFilter() {
        SendFirstRegister(Register.AS5600_CONFIG_REG_CONF_H);
        return AS5600SlowFilter.values()[((RequestSingleRegister() >> AS5600ConfHighRegisterBits.AS5600_CONF_BIT_SF_0.ordinal()) & 0x03)]; // 0x03=0b00000011
    }

    public void setSlowFilter(AS5600SlowFilter _slow_filter) {
        SendFirstRegister(Register.AS5600_CONFIG_REG_CONF_H);
        byte conf_h_raw = RequestSingleRegister();
        WriteOneByte(Register.AS5600_CONFIG_REG_CONF_H, conf_h_raw |= (byte) (_slow_filter.ordinal() << AS5600ConfHighRegisterBits.AS5600_CONF_BIT_SF_0.ordinal()));
    }

    public boolean setSlowFilterVerify(AS5600SlowFilter _slow_filter) {
        setSlowFilter(_slow_filter);
        return (getSlowFilter() == _slow_filter);
    }

    public Runnable enableSlowFilter16x = () -> setSlowFilter(AS5600SlowFilter.AS5600_SLOW_FILTER_16X);
    public Supplier<Boolean> enableSlowFilter16xVerify = () -> setSlowFilterVerify(AS5600SlowFilter.AS5600_SLOW_FILTER_16X);
    public Runnable enableSlowFilter8x = () -> setSlowFilter(AS5600SlowFilter.AS5600_SLOW_FILTER_8X);
    public Supplier<Boolean> enableSlowFilter8xVerify = () -> setSlowFilterVerify(AS5600SlowFilter.AS5600_SLOW_FILTER_8X);
    public Runnable enableSlowFilter4x = () -> setSlowFilter(AS5600SlowFilter.AS5600_SLOW_FILTER_4X);
    public Supplier<Boolean> enableSlowFilter4xVerify = () -> setSlowFilterVerify(AS5600SlowFilter.AS5600_SLOW_FILTER_4X);
    public Runnable enableSlowFilter2x = () -> setSlowFilter(AS5600SlowFilter.AS5600_SLOW_FILTER_2X);
    public Supplier<Boolean> enableSlowFilter2xVerify = () -> setSlowFilterVerify(AS5600SlowFilter.AS5600_SLOW_FILTER_2X);

    public AS5600FastFilterThreshold getFastFilterThreshold() {
        SendFirstRegister(Register.AS5600_CONFIG_REG_CONF_H);
        return AS5600FastFilterThreshold.values()[((RequestSingleRegister() >> AS5600ConfHighRegisterBits.AS5600_CONF_BIT_FTH_0.ordinal()) & 0x07)]; // 0x07=0b00000111
    }

    public void setFastFilterThreshold(AS5600FastFilterThreshold _fast_filter_thredhold) {
        SendFirstRegister(Register.AS5600_CONFIG_REG_CONF_H);
        byte conf_h_raw = RequestSingleRegister();
        WriteOneByte(Register.AS5600_CONFIG_REG_CONF_H, conf_h_raw |= (byte) (_fast_filter_thredhold.ordinal() << AS5600ConfHighRegisterBits.AS5600_CONF_BIT_FTH_0.ordinal()));
    }

    public boolean setFastFilterThresholdVerify(AS5600FastFilterThreshold _fast_filter_thredhold) {
        setFastFilterThreshold(_fast_filter_thredhold);
        return (getFastFilterThreshold() == _fast_filter_thredhold);
    }

    public Runnable enableSlowFilterOnly = () -> setFastFilterThreshold(AS5600FastFilterThreshold.AS5600_FAST_FILTER_THRESHOLD_SLOW_FILTER_ONLY);
    public Supplier<Boolean> enableSlowFilterOnlyVerify = () -> setFastFilterThresholdVerify(AS5600FastFilterThreshold.AS5600_FAST_FILTER_THRESHOLD_SLOW_FILTER_ONLY);
    public Runnable enableFastFilterThreshold6LSB = () -> setFastFilterThreshold(AS5600FastFilterThreshold.AS5600_FAST_FILTER_THRESHOLD_6_LSB);
    public Supplier<Boolean> enableFastFilterThreshold6LSBVerify = () -> setFastFilterThresholdVerify(AS5600FastFilterThreshold.AS5600_FAST_FILTER_THRESHOLD_6_LSB);
    public Runnable enableFastFilterThreshold7LSB = () -> setFastFilterThreshold(AS5600FastFilterThreshold.AS5600_FAST_FILTER_THRESHOLD_7_LSB);
    public Supplier<Boolean> enableFastFilterThreshold7LSBVerify = () -> setFastFilterThresholdVerify(AS5600FastFilterThreshold.AS5600_FAST_FILTER_THRESHOLD_7_LSB);
    public Runnable enableFastFilterThreshold9LSB = () -> setFastFilterThreshold(AS5600FastFilterThreshold.AS5600_FAST_FILTER_THRESHOLD_9_LSB);
    public Supplier<Boolean> enableFastFilterThreshold9LSBVerify = () -> setFastFilterThresholdVerify(AS5600FastFilterThreshold.AS5600_FAST_FILTER_THRESHOLD_9_LSB);
    public Runnable enableFastFilterThreshold18LSB = () -> setFastFilterThreshold(AS5600FastFilterThreshold.AS5600_FAST_FILTER_THRESHOLD_18_LSB);
    public Supplier<Boolean> enableFastFilterThreshold18LSBVerify = () -> setFastFilterThresholdVerify(AS5600FastFilterThreshold.AS5600_FAST_FILTER_THRESHOLD_18_LSB);
    public Runnable enableFastFilterThreshold21LSB = () -> setFastFilterThreshold(AS5600FastFilterThreshold.AS5600_FAST_FILTER_THRESHOLD_21_LSB);
    public Supplier<Boolean> enableFastFilterThreshold21LSBVerify = () -> setFastFilterThresholdVerify(AS5600FastFilterThreshold.AS5600_FAST_FILTER_THRESHOLD_21_LSB);
    public Runnable enableFastFilterThreshold24LSB = () -> setFastFilterThreshold(AS5600FastFilterThreshold.AS5600_FAST_FILTER_THRESHOLD_24_LSB);
    public Supplier<Boolean> enableFastFilterThreshold24LSBVerify = () -> setFastFilterThresholdVerify(AS5600FastFilterThreshold.AS5600_FAST_FILTER_THRESHOLD_24_LSB);
    public Runnable enableFastFilterThreshold10LSB = () -> setFastFilterThreshold(AS5600FastFilterThreshold.AS5600_FAST_FILTER_THRESHOLD_10_LSB);
    public Supplier<Boolean> enableFastFilterThreshold10LSBVerify = () -> setFastFilterThresholdVerify(AS5600FastFilterThreshold.AS5600_FAST_FILTER_THRESHOLD_10_LSB);

    public boolean isWatchdog() {
        SendFirstRegister(Register.AS5600_CONFIG_REG_CONF_H);
        return ((RequestSingleRegister() >> AS5600ConfHighRegisterBits.AS5600_CONF_BIT_WD.ordinal()) & 0x01) == 1;
    }

    public void enableWatchdog() {
        SendFirstRegister(Register.AS5600_CONFIG_REG_CONF_H);
        byte conf_h_raw = RequestSingleRegister();
        WriteOneByte(Register.AS5600_CONFIG_REG_CONF_H, conf_h_raw |= (byte) (1 << AS5600ConfHighRegisterBits.AS5600_CONF_BIT_WD.ordinal()));
    }

    public boolean enableWatchdogVerify() {
        enableWatchdog();
        SendFirstRegister(Register.AS5600_CONFIG_REG_CONF_H);
        return 0x01 == ((RequestSingleRegister() >> AS5600ConfHighRegisterBits.AS5600_CONF_BIT_WD.ordinal()) & 0x01);
    }

    public void disableWatchdog() {
        SendFirstRegister(Register.AS5600_CONFIG_REG_CONF_H);
        byte conf_h_raw = RequestSingleRegister();
        WriteOneByte(Register.AS5600_CONFIG_REG_CONF_H, conf_h_raw &= (byte) ~(1 << AS5600ConfHighRegisterBits.AS5600_CONF_BIT_WD.ordinal()));
    }

    public boolean disableWatchdogVerify() {
        disableWatchdog();
        SendFirstRegister(Register.AS5600_CONFIG_REG_CONF_H);
        return (0x01 != ((RequestSingleRegister() >> AS5600ConfHighRegisterBits.AS5600_CONF_BIT_WD.ordinal()) & 0x01));
    }

    public int getRawAngle() {
        SendFirstRegister(Register.AS5600_OUT_REG_RAW_ANGLE_H);
        return RequestPairRegisters();
    }

    public float getDegreesAngle() {
        return ((float)getRawAngle() * 360) / 4096;
    }

    public double getRadiansAngle() {
        return (getDegreesAngle() * PI) / 180;
    }

    public int getScaledAngle() {
        SendFirstRegister(Register.AS5600_OUT_REG_ANGLE_H);
        return RequestPairRegisters();
    }

    public AS5600StatusReports getStatus() {
        SendFirstRegister(Register.AS5600_STATUS_REG);
        return AS5600StatusReports.fromInt((RequestSingleRegister() >> AS5600StatusRegisterBits.AS5600_STATUS_BIT_MH_3.bVal) & 0x07); // 0x07 = 0b00000111
    }

    public boolean isMagnetDetected() {
        SendFirstRegister(Register.AS5600_STATUS_REG);
        return 0x01 == ((RequestSingleRegister() >> AS5600StatusRegisterBits.AS5600_STATUS_BIT_MD_5.bVal) & 0x01);
    }

    public boolean isMagnetTooWeak() {
        SendFirstRegister(Register.AS5600_STATUS_REG);
        return 0x01 == ((RequestSingleRegister() >> AS5600StatusRegisterBits.AS5600_STATUS_BIT_ML_4.bVal) & 0x01);
    }

    public boolean isMagnetTooStrong() {
        SendFirstRegister(Register.AS5600_STATUS_REG);
        return 0x01 == ((RequestSingleRegister() >> AS5600StatusRegisterBits.AS5600_STATUS_BIT_MH_3.bVal) & 0x01);
    }

    public byte getAutomaticGainControl() {
        SendFirstRegister(Register.AS5600_STATUS_REG_AGC);
        return RequestSingleRegister();
    }

    public int getMagnitude() {
        SendFirstRegister(Register.AS5600_STATUS_REG_MAGNITUDE_H);
        return RequestPairRegisters();
    }

    public AS5600BurnReports burnZeroAndMaxPositions(AS5600SpecialVerifyFlags _use_special_verify) {
        AS5600BurnReports result = AS5600BurnReports.AS5600_BURN_REPROT_SENSOR_NOT_CONNECTED;

        if (isConnected()) { // Если датчик подключен
            // Собираем значениях из критически выжных регистров
            byte burn_count = getBurnPositionsCount();
            int z_pos = getZeroPosition();
            int m_pos = getMaxPosition();
            if (burn_count < AS5600_MAX_VALUE_ZMCO) { // Если ресурс для записи не исчерпан
                if (z_pos != 0 && m_pos != 0) { // Если значения начального и максимального положения не 0
                    // Наличие магнита проверяем НА ПОСЛЕДНЕМ ШАГЕ, перед отправлением команды на запись!
                    if (isMagnetDetected()) { // Если магнит обнаружен
                        WriteOneByte(Register.AS5600_BURN_REG, Command.AS5600_CMD_BURN_ANGLE.bVal); // Отправляем команду записи
                        if (_use_special_verify.equals(AS5600SpecialVerifyFlags.AS5600_FLAG_SPECIAL_VERIFY_ENABLE)) { // Если используется проверка записанного
                            loadSavedValues(); // Загружаем из памяти ранее записанные данные
                            // Получаем загруженные данные для сравнения
                            int z_pos_now = getZeroPosition();
                            int m_pos_now = getMaxPosition();
                            if (z_pos == z_pos_now && m_pos == m_pos_now) { // Если записываемые данные совпадают с сохраненными
                                result = AS5600BurnReports.AS5600_BURN_REPROT_WRITE_OK;
                            } else {
                                result = AS5600BurnReports.AS5600_BURN_REPROT_WRITE_WRONG;
                            }
                        } else {
                            result = AS5600BurnReports.AS5600_BURN_REPROT_WRITE_OK_WITHOUT_VERIFY;
                        }
                    } else {
                        result = AS5600BurnReports.AS5600_BURN_REPROT_MAGNET_NOT_FOUND;
                    }
                } else {
                    result = AS5600BurnReports.AS5600_BURN_REPROT_ZPOS_MPOS_NOT_SET;
                }
            } else {
                result = AS5600BurnReports.AS5600_BURN_REPROT_ATTEMPTS_ENDED;
                result = AS5600BurnReports.AS5600_BURN_REPROT_ATTEMPTS_ENDED;
            }
        }

        return result;
    }

    AS5600BurnReports burnMaxAngleAndConfigurationValue(AS5600SpecialVerifyFlags _use_special_verify) {
        AS5600BurnReports result = AS5600BurnReports.AS5600_BURN_REPROT_SENSOR_NOT_CONNECTED;

        if (isConnected()) { // Если датчик подключен
            // Собираем значениях из критически выжных регистров
            byte burn_count = getBurnPositionsCount();
            int m_ang = getMaxAngle();
            int conf = getRawConfigurationValue();
            if (burn_count == 0) { // Если ресурс для записи не исчерпан
                if (getMaxAngle() >= AS5600_MIN_ANGLE_VALUE_DEC) { // Если значение угла подходит
                    // Наличие магнита проверяем НА ПОСЛЕДНЕМ ШАГЕ, перед отправлением команды на запись!
                    if (isMagnetDetected()) { // Если магнит обнаружен
                        WriteOneByte(Register.AS5600_BURN_REG, AS5600_CMD_BURN_SETTINGS); // Отправляем команду записи настроек
                        if (_use_special_verify.equals(AS5600SpecialVerifyFlags.AS5600_FLAG_SPECIAL_VERIFY_ENABLE)) { // Если используется проверка записанного
                            loadSavedValues(); // Загружаем из памяти ранее записанные данные
                            // Получаем загруженные данные для сравнения
                            int m_ang_now = getMaxAngle();
                            int conf_now = getRawConfigurationValue();
                            if (m_ang == m_ang_now && conf == conf_now) { // Если записываемые данные совпадают с сохраненными
                                result = AS5600BurnReports.AS5600_BURN_REPROT_WRITE_OK;
                            } else {
                                result = AS5600BurnReports.AS5600_BURN_REPROT_WRITE_WRONG;
                            }
                        } else {
                            result = AS5600BurnReports.AS5600_BURN_REPROT_WRITE_OK_WITHOUT_VERIFY;
                        }
                    } else {
                        result = AS5600BurnReports.AS5600_BURN_REPROT_MAGNET_NOT_FOUND;
                    }
                } else {
                    result = AS5600BurnReports.AS5600_BURN_REPROT_ANGLE_VALUE_TOO_SMALL;
                }
            } else { // Если ZMCO > 0, то записать можно только КОНФИГУРАЦИИ без МАКСИМАЛЬНОГО угла
                // Наличие магнита проверяем НА ПОСЛЕДНЕМ ШАГЕ, перед отправлением команды на запись!
                if (isMagnetDetected()) { // Если магнит обнаружен
                    WriteOneByte(Register.AS5600_BURN_REG, AS5600_CMD_BURN_SETTINGS); // Отправляем команду записи настроек
                    if (_use_special_verify.equals(AS5600SpecialVerifyFlags.AS5600_FLAG_SPECIAL_VERIFY_ENABLE)) { // Если используется проверка записанного
                        loadSavedValues(); // Загружаем из памяти ранее записанные данные
                        // Получаем загруженные данные для сравнения
                        int conf_now = getRawConfigurationValue();
                        if (conf == conf_now) { // Если записываемые данные совпадают с сохраненными
                            result = AS5600BurnReports.AS5600_BURN_REPROT_WRITE_OK_WITHOUT_MAXANGLE;
                        } else {
                            result = AS5600BurnReports.AS5600_BURN_REPROT_WRITE_WRONG;
                        }
                    } else {
                        result = AS5600BurnReports.AS5600_BURN_REPROT_WRITE_OK_WITHOUT_VERIFY_WITHOUT_MAXANGLE;
                    }
                } else {
                    result = AS5600BurnReports.AS5600_BURN_REPROT_MAGNET_NOT_FOUND;
                }
            }
        }

        return result;
    }

    public AS5600(I2cDeviceSynch deviceClient, boolean deviceClientIsOwned) {
        super(deviceClient, deviceClientIsOwned);

        this.setOptimalReadWindow();
        this.deviceClient.setI2cAddress(ADDRESS);

        super.registerArmingStateCallback(false);
        this.deviceClient.engage();
    }
}
