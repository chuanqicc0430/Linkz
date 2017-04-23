package cn.net.cvtt.lian.common.serialization.bytecode.instruction;

import java.io.DataInput;
import java.io.IOException;

/**
 * 带一个Short类型操作值的操作码，这样的类型在204个操作码中占了14个，主要是为了long、double类型服务的
 * 
 * @author 
 * 
 */
public class ShortInstruction extends ConstantInfoInstruction {

	private int value;

	public ShortInstruction(byte opcode) {
		super(opcode);
	}

	@Override
	public void read(DataInput input) throws IOException {
		value = input.readUnsignedShort();
		super.length = 3;
	}

	public int getValue() {
		return value;
	}

}
