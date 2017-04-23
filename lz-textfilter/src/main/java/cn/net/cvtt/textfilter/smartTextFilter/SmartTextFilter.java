package cn.net.cvtt.textfilter.smartTextFilter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.configuration.ConfigurationException;
import cn.net.cvtt.lian.common.initialization.InitialUtil;
import cn.net.cvtt.lian.common.initialization.Initializer;
import cn.net.cvtt.lian.common.util.EnumParser;
import cn.net.cvtt.lian.common.util.Flags;
import cn.net.cvtt.lian.common.util.StringUtils;
import cn.net.cvtt.resource.database.DataRow;
import cn.net.cvtt.resource.database.DataTable;
import cn.net.cvtt.resource.database.DatabaseProxy;
import cn.net.cvtt.resource.route.ResourceFactory;
import cn.net.cvtt.textfilter.FilterModeEnum;
import cn.net.cvtt.textfilter.FilterTypeEnum;
import cn.net.cvtt.textfilter.HarmonizedResult;
import cn.net.cvtt.textfilter.HarmonizedResult1;
import cn.net.cvtt.textfilter.HarmonizedWords;
import cn.net.cvtt.textfilter.LoadTextFilterProperties;
import cn.net.cvtt.textfilter.smartTextFilter.context.FilterContext;
import cn.net.cvtt.textfilter.smartTextFilter.context.HmzContextFiltered;
import cn.net.cvtt.textfilter.smartTextFilter.context.HmzIdentityChain;
import cn.net.cvtt.textfilter.smartTextFilter.normalizer.ContentNormalizer;
import cn.net.cvtt.textfilter.smartTextFilter.normalizer.HtmlReader;
import cn.net.cvtt.textfilter.smartTextFilter.tree.HmzTree;

public class SmartTextFilter {

	private static final Logger logger = LoggerFactory.getLogger(SmartTextFilter.class);

	private static HmzTree tree;


	@Initializer
	public static void initialize() throws Exception {
		InitialUtil.init(HmzContextFiltered.class, ResourceFactory.class);
		loadSensitiveWords();
	}

	private static void loadSensitiveWords() throws SQLException, IOException {
		DatabaseProxy proxy = ResourceFactory.getDatabaseProxy("RESOURCEDB");
		String sql = "SELECT * FROM RES_SensitiveWords";
		DataTable dt = proxy.executeTable(sql);

		List<HarmonizedWords> tables = new ArrayList<HarmonizedWords>();
		if (dt != null && dt.getRowCount() > 0) {
			for (DataRow row : dt.getRows()) {
				HarmonizedWords hzWords = new HarmonizedWords();
				hzWords.setWord(row.getString("Word"));
				hzWords.setId(row.getInt("Id"));
				hzWords.setFilterMode((FilterModeEnum) EnumParser.parseInt(FilterModeEnum.class, row.getInt("FilterMode")));
				hzWords.setFilterType(new Flags<FilterTypeEnum>(row.getInt("FilterType")));
				hzWords.setTreatment(row.getByte("Treatment"));
				hzWords.setRelateWords(row.getString("RelateWords"));
				hzWords.setEnabled(row.getBoolean("Enabled"));

				tables.add(hzWords);
			}

			tree = new HmzTree(tables);

			String specialCharacters = StringUtils.EMPTY;
			try {
				specialCharacters = LoadTextFilterProperties.getInstance().getPropValue("SpecialCharacters");
			} catch (ConfigurationException e) {
				e.printStackTrace();
			}
			HmzContextFiltered.loadExtraIgnoreCharacters(specialCharacters);
		}
	}

	public static HarmonizedResult1<Boolean, String, Byte> isHarmonizedThree(StringBuffer stream, FilterTypeEnum filterType) {
		logger.info("SmartTextFilter IsHarmonized #5.2");
		HarmonizedResult1<Boolean, String, Byte> result = new HarmonizedResult1<Boolean, String, Byte>();
		String filterWord = StringUtils.EMPTY;
		byte treatment = Byte.MIN_VALUE;

		if (stream == null || stream.length() == 0) {
			result.RValue = filterWord;
			result.SValue = treatment;
			result.TValue = false;
			return result;
		}
		return isHarmonizedThree(new StringReader(stream.toString()), filterType);
	}

	public static HarmonizedResult1<Boolean, String, Byte> isHarmonizedThree(String text, FilterTypeEnum filterType) {
		logger.info("SmartTextFilter IsHarmonized #5.1");
		HarmonizedResult1<Boolean, String, Byte> result = new HarmonizedResult1<Boolean, String, Byte>();

		if (StringUtils.isNullOrEmpty(text)) {
			// filterWord = string.Empty;
			// treatment = byte.MinValue;
			result.RValue = StringUtils.EMPTY;
			result.TValue = false;
			result.SValue = Byte.MIN_VALUE;
			return result;
		}

		return isHarmonizedThree(new StringReader(text), filterType);
	}

	private static HarmonizedResult1<Boolean, String, Byte> isHarmonizedThree(Reader reader, FilterTypeEnum filterType) {

		HarmonizedResult<List<HmzIdentityChain>, Byte> res = isHarmonized(reader, filterType, false);
		List<HmzIdentityChain> block = res.TValue;
		byte treatment = res.RValue;
		String filterWord = StringUtils.EMPTY;
		HarmonizedResult1<Boolean, String, Byte> result = new HarmonizedResult1<Boolean, String, Byte>();
		if ((block == null || block.size() < 1)) {
			// filterWord = string.Empty;
			// return false;
			result.RValue = filterWord;
			result.SValue = treatment;
			result.TValue = false;
			return result;
		}

		HarmonizedResult<String, Boolean> res1 = getHitWords(block);
		result.TValue = res1.RValue;
		result.RValue = res1.TValue;
		result.SValue = treatment;
		// bool dummy;
		// filterWord = getHitWords(block);
		return result;
	}

	public static boolean isHarmonizedBoolean(StringBuffer stream, FilterTypeEnum filterType) {
		logger.info("SmartTextFilter IsHarmonized #1.2");
		return isHarmonized(stream, filterType).TValue;
	}

	public static HarmonizedResult<Boolean, String> isHarmonized(StringBuffer stream, FilterTypeEnum filterType) {
		logger.info("SmartTextFilter IsHarmonized #4.2");
		HarmonizedResult<Boolean, String> result = new HarmonizedResult<Boolean, String>();
		String filterWord = StringUtils.EMPTY;
		if (stream == null || stream.length() == 0) {
			result.RValue = filterWord;
			result.TValue = false;
			// filterWord = string.Empty;
			return result;
		}

		return isHarmonized(new StringReader(stream.toString()), filterType);
	}

	public static boolean isHarmonizedBoolean(String text, FilterTypeEnum filterType) {
		logger.info("SmartTextFilter IsHarmonized #1.1");
		// string filterWord = string.Empty;
		HarmonizedResult<Boolean, String> result = isHarmonized(text, filterType);
		return result.TValue;
	}

	public static HarmonizedResult<Boolean, String> isHarmonized(String text, FilterTypeEnum filterType) {
		logger.info("SmartTextFilter IsHarmonized #4.1");
		HarmonizedResult<Boolean, String> result = new HarmonizedResult<Boolean, String>();
		if (StringUtils.isNullOrEmpty(text)) {
			// filterWord = string.Empty;
			// return false;
			result.TValue = false;
			result.RValue = StringUtils.EMPTY;
			return result;
		}

		return isHarmonized(new StringReader(text), filterType);
	}

	private static HarmonizedResult<Boolean, String> isHarmonized(Reader reader, FilterTypeEnum filterType) {
		HarmonizedResult<Boolean, String> result = new HarmonizedResult<Boolean, String>();
		HarmonizedResult<List<HmzIdentityChain>, Byte> r = isHarmonizedList(reader, filterType, true);
		List<HmzIdentityChain> block = r.TValue;
		if (block == null || block.size() < 1) {
			// filterWord = string.Empty;
			// return false;
			result.RValue = StringUtils.EMPTY;
			result.TValue = false;
			return result;
		}
		HarmonizedResult<String, Boolean> HitResult = getHitWords(block);
		if (HitResult.treatment == 127) {
			result.treatment = r.RValue;
		} else {
			result.treatment = HitResult.treatment;
		}
		result.RValue = HitResult.TValue;
		result.TValue = HitResult.RValue;
		// bool dummy;
		// filterWord = getHitWords(block);
		return result;
	}

	public static HarmonizedResult<List<HmzIdentityChain>, Byte> isHarmonizedList(Reader reader, FilterTypeEnum filterType, boolean haltOnBlock) {
		logger.info("SmartTextFilter IsHarmonized #0.1");
		HarmonizedResult<List<HmzIdentityChain>, Byte> result = isHarmonized(reader, filterType, false);
		return result;
	}

	public static HarmonizedResult<List<HmzIdentityChain>, Byte> isHarmonized(Reader reader, FilterTypeEnum filterType, boolean haltOnBlock) {
		logger.info("SmartTextFilter IsHarmonized #0.0");
		HarmonizedResult<List<HmzIdentityChain>, Byte> result = new HarmonizedResult<List<HmzIdentityChain>, Byte>();
		byte treatment = Byte.MIN_VALUE;

		if (reader == null) {
			result.RValue = treatment;
			result.TValue = null;
			return result;
		}

		try {
			HmzTree hmzTree = tree;
			// FeatureDigestImp fdImp = FeatureDigestStaticShell.getDigest();

			if (hmzTree == null) {
				result.RValue = treatment;
				result.TValue = null;
				return result;
			}
			if ((filterType.intValue() & FilterTypeEnum.HtmlContent.intValue()) != FilterTypeEnum.None.intValue()) {
				filterType = FilterTypeEnum.intConvert(filterType.intValue() & ~FilterTypeEnum.HtmlContent.intValue());
				reader = new HtmlReader(reader);
			}
			StringBuilder charList = new StringBuilder();
			ContentNormalizer content = new ContentNormalizer(reader);
			FilterContext filter = hmzTree.createContext();
			for (char c = content.read(); c != Character.MIN_VALUE; c = content.read()) {
				charList.append(c);
			}
			// if (featureDigestEnabled && HmzFeatureDigest.notNull()) {
			// Guid fdgt = fdImp.getDigest(charList.toString());
			// if (HmzFeatureDigest.isHit(fdgt)) {
			// treatment = 30;
			// HmzIdentityChain ifdc = new HmzIdentityChain(new HmzIdentityDigest(fdgt));
			// if (haltOnBlock) {
			// List<HmzIdentityChain> ids = new ArrayList<HmzIdentityChain>(1);
			// ids.add(ifdc);
			// result.TValue = ids;
			// result.RValue = treatment;
			// return result;
			// }
			// filter.getHit().add(ifdc);
			// }
			// }
			if (hmzTree != null) {
				char next = Character.MIN_VALUE;
				// for (char c : charList.toString().toCharArray()) {
				for (int i = 0; i < charList.length(); i++) {
					if (i < charList.length() - 1) {
						next = charList.charAt(i + 1);
					} else {
						next = Character.MIN_VALUE;
					}
					// if (filter.next((char) c, filterType) && haltOnBlock) {
					if (filter.next(charList.charAt(i), filterType, next) && haltOnBlock) {
						HarmonizedResult<List<HmzIdentityChain>, Byte> harm = getBlockHit(filter.getHit());
						treatment = harm.RValue;
						List<HmzIdentityChain> block = harm.TValue;
						if (block != null && block.size() > 0) {
							for (HmzIdentityChain hic : block) {
								if (hic.getBlock()) {
									result.TValue = block;
									result.treatment = treatment;
									result.RValue = treatment;
									return result;
								}
							}
						}
					}
				}

				if (filter.complete(filterType) && !haltOnBlock) {
					HarmonizedResult<List<HmzIdentityChain>, Byte> hr = getBlockHit(filter.getHit());
					List<HmzIdentityChain> block = hr.TValue;
					treatment = hr.RValue;
					if (block != null && block.size() > 0) {
						result.TValue = block;
						result.RValue = treatment;
						return result;
					}
				}
				// return haltOnBlock ? null : filter.Hit;
				if (haltOnBlock)
					result.TValue = null;
				else
					result.TValue = filter.getHit();
				result.RValue = treatment;
				return result;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("stf execution error ", ex);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				logger.error("reader close error ", e);
			}
		}
		result.TValue = null;
		result.RValue = treatment;
		return result;
	}

	// public static HarmonizedResult<String, Boolean>
	// getHitWords(List<HmzIdentityChain> hit, out bool block)
	public static HarmonizedResult<String, Boolean> getHitWords(List<HmzIdentityChain> hit) {
		HarmonizedResult<String, Boolean> result = new HarmonizedResult<String, Boolean>();
		boolean block = false;

		if (hit == null || hit.size() < 1) {
			result.TValue = StringUtils.EMPTY;
			result.RValue = block;
			result.treatment = 127;
			return result;
		}

		try {
			List<HmzIdentityChain> removeHit = new ArrayList<HmzIdentityChain>();
			for (HmzIdentityChain chain : hit) {
				if (chain.getMode() == FilterModeEnum.MasterSlave) {
					HarmonizedWords words = (HarmonizedWords) chain.getIdentity();
					boolean isFind = false;
					for (HmzIdentityChain hmzChain : hit) {
						if (hmzChain.getMode() == FilterModeEnum.Filtered) {
							if (hmzChain.get(0).getTarget().equals(words.getRelateWords()[0])) {
								isFind = true;
								if (hmzChain.getBlock()) {
									removeHit.add(hmzChain);
								} else {
									removeHit.add(chain);
								}
							}
						}
					}
					if (!isFind) {
						boolean isPrimary = false;
						for (int i = 0; i < chain.getCount(); i++) {
							if (chain.get(0).getTarget().equals(words.getRelateWords()[0])) {
								isPrimary = true;
							}
						}
						if (!isPrimary) {
							removeHit.add(chain);
						}
					}
				}
			}
			for (HmzIdentityChain chain : removeHit) {
				hit.remove(chain);
			}
			StringBuilder str = new StringBuilder();
			for (int i = 0; i < hit.size(); ++i) {
				if (hit.get(i).getCount() < 1)
					continue;

				HmzIdentityChain chain = hit.get(i);
				if (result.treatment != 0 && result.treatment > chain.getTreatment()) {
					result.treatment = chain.getTreatment();
				}

				if (hit.get(i).getBlock())
					block = true;

				str.append('{');
				switch (hit.get(i).getMode()) {
				case Composite:
					str.append('C');
					break;
				case Exact:
					str.append('E');
					break;
				case Filtered:
					str.append('F');
					break;
				case Language:
					str.append('L');
					break;
				case Phrase:
					str.append('P');
					break;
				case MasterSlave:
					str.append('M');
					break;
				case None:
					str.append('N');
					break;
				default:
					str.append('U');
					break;
				}
				str.append(':');
				str.append(hit.get(i).getBlock() ? 'B' : '-');
				// str.append(hit.get(i).getLog() ? 'L' : '-');
				str.append('L');
				str.append(hit.get(i).getTreatment());
				str.append('<');
				for (int j = 0; j < hit.get(i).getCount(); ++j) {
					str.append(hit.get(i).get(j).getTarget());
					str.append(',');
				}
				str.deleteCharAt(str.length() - 1);
				str.append(">}");
			}
			result.TValue = str.toString();
			result.RValue = block;
			return result;
		} catch (Exception ex) {
			logger.error("failed to get hit targets", ex);
			result.TValue = "{-:xx<>}";
			result.RValue = block;
			return result;
		}
	}

	// private static List<HmzIdentityChain> getBlockHit(List<HmzIdentityChain>
	// hit, out byte treatment)
	private static HarmonizedResult<List<HmzIdentityChain>, Byte> getBlockHit(List<HmzIdentityChain> hit) {
		HarmonizedResult<List<HmzIdentityChain>, Byte> result = new HarmonizedResult<List<HmzIdentityChain>, Byte>();
		byte treatment = Byte.MAX_VALUE;
		if (hit == null || hit.size() < 1) {
			treatment = Byte.MAX_VALUE;
			result.TValue = null;
			result.RValue = treatment;
			return result;
		}

		List<HmzIdentityChain> block = null;
		for (int i = 0; i < hit.size(); ++i) {
			HmzIdentityChain chain = hit.get(i);
			if (chain.getTreatment() < treatment)
				treatment = chain.getTreatment();
			// if (chain.getBlock() || chain.getLog()) {
			if (chain.getEnabled()) {
				if (block == null)
					block = new ArrayList<HmzIdentityChain>();
				block.add(chain);
			}
		}
		result.RValue = treatment;
		result.TValue = block;
		return result;
	}

}
