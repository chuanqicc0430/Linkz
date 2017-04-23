package cn.net.cvtt.lian.common.serialization.bytecode.instruction;

import java.io.DataInput;
import java.io.IOException;

/**
 * 为invokeinterface指令准备的<br>
 * INVOKEINTERFACE((byte) 0xb9, 1,"invokeinterface", "调用接口方法。"),
 * 
 * @author 
 * 
 */
public class InvokeInterfaceInstruction extends ConstantInfoInstruction {

	private int value;
	private int count;

	public InvokeInterfaceInstruction(byte opcode) {
		super(opcode);
	}

	@Override
	public void read(DataInput input) throws IOException {
		value = input.readUnsignedShort();
		count = input.readUnsignedByte();
		// 规范上说,invokerface指令值的第二位为0，要丢弃
		input.readByte();
		super.length = 5;
	}

	public int getValue() {
		return value;
	}

	public int getCount() {
		return count;
	}

}
