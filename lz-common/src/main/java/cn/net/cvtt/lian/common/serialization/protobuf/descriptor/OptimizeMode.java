package cn.net.cvtt.lian.common.serialization.protobuf.descriptor;

import cn.net.cvtt.lian.common.util.EnumInteger;

/**
 * OptimizeMode
 * 
 * @author  
 */
public enum OptimizeMode implements EnumInteger
{
  SPEED(1),
  CODE_SIZE(2),
  LITE_RUNTIME(3),
  ;
  
  private int value;
  
  OptimizeMode(int value)
  {
    this.value = value;
  }
  
  @Override
  public int intValue()
  {
    return value;
  }
}
