package cn.net.cvtt.lian.common.serialization.bytecode.instruction;

import java.io.DataInput;
import java.io.IOException;

/**
 * 用于multianewarray指令时使用<br>
 * MULTIANEWARRAY((byte) 0xc5, 1,"multianewarray",
 * "创建指定类型和指定维度的多维数组（执行该指令时，操作栈中必须包含各维度的长度值），并将其引用值压入栈顶。"),
 * 
 * @author 
 * 
 */
public class MultianewarrayInstruction extends ConstantInfoInstruction {

	private int value;

	public MultianewarrayInstruction(byte opcode) {
		super(opcode);
	}

	@Override
	public void read(DataInput input) throws IOException {
		value = input.readUnsignedShort();
		value = input.readUnsignedByte();
		super.length = 4;
	}

	public int getValue() {
		return value;
	}

}
