package cn.net.cvtt.lian.common.serialization.bytecode.instruction;

import java.io.DataInput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 为LookupSwitch执行服务的<br>
 * LOOKUPSWITCH((byte) 0xab, 1,"lookupswitch",
 * "用于switch条件跳转，case值不连续（可变长度指令）。"),
 * 
 * @author 
 * 
 */
public class LookupSwitchInstruction extends Instruction {

	private int defaultIndex;
	private Map<Integer, Integer> caseMap;

	public LookupSwitchInstruction(byte opcode) {
		super(opcode);
	}

	@Override
	public void read(DataInput input) throws IOException {

		// code代码对齐,4byte(+1是为了越过当前标志位)
		int bytesToRead = paddingBytes(super.offset + 1);
		for (int i = 0; i < bytesToRead; i++) {
			input.readByte();
		}

		caseMap = new HashMap<Integer, Integer>();

		defaultIndex = input.readInt();
		int length = input.readInt();

		for (int i = 0; i < length; i++) {
			caseMap.put(input.readInt(), input.readInt());
		}
		super.length = bytesToRead + 9 + 8 * caseMap.size();
	}

	public int getDefaultOffset() {
		return defaultIndex;
	}

	public final Map<Integer, Integer> getCaseMap() {
		return caseMap;
	}

	private int paddingBytes(int bytesCount) {
		int bytesToPad = 4 - bytesCount % 4;
		return (bytesToPad == 4) ? 0 : bytesToPad;
	}
}
