package cn.net.cvtt.lian.common.serialization.bytecode.instruction;

public/**
 * 操作码对应的操作指令类型
 * 
 * @author 
 * 
 */
enum InstructionEnum {
	SIMPLE_INSTRUCTION() {
		@Override
		public Instruction newInstance(byte opcode, boolean isWide) {
			return new SimpleInstruction(opcode);
		}
	},
	BYTE_INSTRUCTION() {
		@Override
		public Instruction newInstance(byte opcode, boolean isWide) {
			return new ByteInstruction(opcode, isWide);
		}
	},
	IINC_INSTRUCTION() {
		@Override
		public Instruction newInstance(byte opcode, boolean isWide) {
			return new IINCInstruction(opcode, isWide);
		}
	},
	SHORT_INSTRUCTION() {
		@Override
		public Instruction newInstance(byte opcode, boolean isWide) {
			return new ShortInstruction(opcode);
		}
	},
	INT_INSTRUCTION() {
		@Override
		public Instruction newInstance(byte opcode, boolean isWide) {
			return new IntInstruction(opcode);
		}
	},
	BRANCH_INSTRUCTION() {
		@Override
		public Instruction newInstance(byte opcode, boolean isWide) {
			return new BranchInstruction(opcode);
		}
	},
	TABLESWITCH_INSTRUCTION() {
		@Override
		public Instruction newInstance(byte opcode, boolean isWide) {
			return new TableSwitchInstruction(opcode);
		}
	},
	LOOKUPSWITCH_INSTRUCTION() {
		@Override
		public Instruction newInstance(byte opcode, boolean isWide) {
			return new LookupSwitchInstruction(opcode);
		}
	},
	INVOKEINTERFACE_INSTRUCTION() {
		@Override
		public Instruction newInstance(byte opcode, boolean isWide) {
			return new InvokeInterfaceInstruction(opcode);
		}
	},
	MULTIANEWARRAY_INSTRUCTION() {
		@Override
		public Instruction newInstance(byte opcode, boolean isWide) {
			return new MultianewarrayInstruction(opcode);
		}
	};
	public abstract Instruction newInstance(byte opcode, boolean isWide);
}
