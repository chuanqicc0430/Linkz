package cn.net.cvtt.lian.common.serialization.bytecode.instruction;

import java.io.DataInput;
import java.io.IOException;

/**
 * 最最最简单的操作指令，简单到没有任何操作值，仅有操作码，这样的类型在204个操作码中占了154个，代表大多数
 * 
 * @author 
 * 
 */
public class SimpleInstruction extends Instruction {

	public SimpleInstruction(byte opcode) {
		super(opcode);
	}

	@Override
	public void read(DataInput input) throws IOException {
		// 什么都不用做了，因为不需要读取任何操作值
		super.length = 1;
	}
}
