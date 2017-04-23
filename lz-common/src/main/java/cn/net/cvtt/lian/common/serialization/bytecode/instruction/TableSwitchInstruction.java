package cn.net.cvtt.lian.common.serialization.bytecode.instruction;

import java.io.DataInput;
import java.io.IOException;

/**
 * 为TableSwitch执行服务的<br>
 * TABLESWITCH((byte) 0xaa, 1,"tableswitch", "用于switch条件跳转，case值连续（可变长度指令"),
 * 
 * @author 
 * 
 */
public class TableSwitchInstruction extends Instruction {

	private int defaultOffset;
	private int lowByte;
	private int highByte;
	private int[] jumpOffsets;

	public TableSwitchInstruction(byte opcode) {
		super(opcode);
	}

	@Override
	public void read(DataInput input) throws IOException {

		// code代码对齐,4byte(+1是为了越过当前标志位)
		int bytesToRead = paddingBytes(super.offset + 1);
		for (int i = 0; i < bytesToRead; i++) {
			input.readByte();
		}

		defaultOffset = input.readInt();
		lowByte = input.readInt();
		highByte = input.readInt();

		int numberOfOffsets = highByte - lowByte + 1;
		jumpOffsets = new int[numberOfOffsets];

		for (int i = 0; i < numberOfOffsets; i++) {
			jumpOffsets[i] = input.readInt();
		}

		// 13为opcode+defaultOffset+lowByte+highByte占用的byte长度
		super.length = bytesToRead + 13 + 4 * jumpOffsets.length;
	}

	private int paddingBytes(int bytesCount) {
		int bytesToPad = 4 - bytesCount % 4;
		return (bytesToPad == 4) ? 0 : bytesToPad;
	}

	public int getDefaultOffset() {
		return defaultOffset;
	}

	public int getLowByte() {
		return lowByte;
	}

	public int getHighByte() {
		return highByte;
	}

	public int[] getJumpOffsets() {
		return jumpOffsets;
	}

}
