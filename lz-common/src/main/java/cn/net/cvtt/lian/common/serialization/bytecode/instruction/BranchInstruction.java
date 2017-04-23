package cn.net.cvtt.lian.common.serialization.bytecode.instruction;

import java.io.DataInput;
import java.io.IOException;

/**
 * 用于分支语句的操作码，这样的类型在204个操作码中占了18个，他也同样读取两个byte位，但他读取的是有符号的
 * 
 * @author 
 * 
 */
public class BranchInstruction extends Instruction {

	private int value;

	public BranchInstruction(byte opcode) {
		super(opcode);
		super.length = 3;
	}

	public BranchInstruction(byte opcode, int value) {
		super(opcode);
		this.value = value;
		super.length = 3;
	}

	@Override
	public void read(DataInput input) throws IOException {
		value = input.readShort();
		super.length = 3;
	}

	public int getValue() {
		return value;
	}

}
