package cn.net.cvtt.textFilter;

import java.io.StringReader;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cn.net.cvtt.lian.common.initialization.InitialUtil;
import cn.net.cvtt.textfilter.FilterTypeEnum;
import cn.net.cvtt.textfilter.HarmonizedResult;
import cn.net.cvtt.textfilter.HarmonizedResult1;
import cn.net.cvtt.textfilter.smartTextFilter.SmartTextFilter;
import cn.net.cvtt.textfilter.smartTextFilter.context.HmzIdentityChain;

public class SmartTextFilterTest {

	@Before
	public void setUp() throws Exception {
		InitialUtil.init(SmartTextFilter.class);
	}

	@Test
	public void testIsHarmonizedThreeStringBufferFilterTypeEnum() {
		HarmonizedResult1<Boolean, String, Byte> res = SmartTextFilter.isHarmonizedThree(new StringBuffer("大家"), FilterTypeEnum.PersonalInfo);
		System.out.println(res.RValue);
		System.out.println(res.TValue);
		System.out.println(res.SValue);
	}

	@Test
	public void testIsHarmonizedThreeStringFilterTypeEnum() {
		HarmonizedResult1<Boolean, String, Byte> res = SmartTextFilter.isHarmonizedThree("贼民", FilterTypeEnum.DialogMessage);
		System.out.println(res.RValue);
		System.out.println(res.TValue);
		System.out.println(res.SValue);
	}

	@Test
	public void testIsHarmonizedStringBufferIICUserTypeFilterTypeEnum() {
		HarmonizedResult<Boolean, String> res = SmartTextFilter.isHarmonized(new StringBuffer("贼民"), FilterTypeEnum.DialogMessage);
		System.out.println(res.TValue);
		System.out.println(res.RValue);
	}

	@Test
	public void testIsHarmonizedBooleanStringBufferFilterTypeEnum() {
		boolean res = SmartTextFilter.isHarmonizedBoolean(new StringBuffer("贼sdf民"), FilterTypeEnum.DialogMessage);
		System.out.println(res);
	}

	@Test
	public void testIsHarmonizedStringBufferFilterTypeEnum() {
		HarmonizedResult<Boolean, String> res = SmartTextFilter.isHarmonized(new StringBuffer("贼民"), FilterTypeEnum.DialogMessage);

		System.out.println(res.RValue);
		System.out.println(res.TValue);
	}

	@Test
	public void testIsHarmonizedBooleanStringFilterTypeEnum() {
		boolean res = SmartTextFilter.isHarmonizedBoolean("贼民", FilterTypeEnum.DialogMessage);
		System.out.println(res);
	}

	@Test
	public void testIsHarmonizedStringFilterTypeEnum() {
		HarmonizedResult<Boolean, String> res = SmartTextFilter.isHarmonized("贼民", FilterTypeEnum.DialogMessage);
		System.out.println(res.RValue);
		System.out.println(res.TValue);
	}

	@Test
	public void testIsHarmonizedList() {
		HarmonizedResult<List<HmzIdentityChain>, Byte> res = SmartTextFilter.isHarmonizedList(new StringReader("贼民"), FilterTypeEnum.DialogMessage, true);
		for (HmzIdentityChain chain : res.TValue) {
			System.out.println(chain + "                " + chain.getIdentity());
		}
	}

	/*
	 * @Test public void testIsHarmonizedReaderFilterTypeEnumBoolean() { HarmonizedResult<List<HmzIdentityChain>, Byte> result = SmartTextFilter.isHarmonized(new StringReader("卖逼"), FilterTypeEnum.None, true); System.out.println(result); System.out.println(result.TValue); System.out.println(result.RValue); }
	 */

	/*
	 * @Test public void testGetHitWords() { SmartTextFilter.getHitWords(); }
	 */

}
