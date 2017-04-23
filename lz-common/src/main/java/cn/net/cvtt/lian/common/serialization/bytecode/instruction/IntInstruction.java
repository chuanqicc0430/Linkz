package cn.net.cvtt.lian.common.serialization.bytecode.instruction;

import java.io.DataInput;
import java.io.IOException;

/**
 * 用于分支语句的操作码，这样的类型在204个操作码中占了2个，GOTO_W以及JSR_W
 * 
 * @author 
 * 
 */
public class IntInstruction extends ConstantInfoInstruction {

	private int value;

	public IntInstruction(byte opcode) {
		super(opcode);
	}

	@Override
	public void read(DataInput input) throws IOException {
		value = input.readInt();
		super.length = 5;
	}

	public int getValue() {
		return value;
	}

}
