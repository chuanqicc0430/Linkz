package cn.net.cvtt.resource.route.context;

/**
 * 无状态服务使用, 完全依赖输入参数k
 * 
 * @author zongchuanqi
 */
public final class NullContext extends ApplicationCtx {
	public static final NullContext INSTANCE = new NullContext();
	public static final byte[] EMPTY_BUFFER = new byte[0];

	@Override
	public void decode(byte[] buffer) {
	}

	@Override
	public byte[] encode(int demands) {
		return EMPTY_BUFFER;
	}

	@Override
	public ContextUri getContextUri() {
		// TODO Auto-generated method stub
		return null;
	}
}
