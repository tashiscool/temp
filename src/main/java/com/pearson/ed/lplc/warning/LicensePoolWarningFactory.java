package com.pearson.ed.lplc.warning;

import java.util.Properties;

public class LicensePoolWarningFactory {

	private String defaultKey;
	private Properties codeDescProperties;

	public String getDefaultKey() {
		return defaultKey;
	}

	public void setDefaultKey(String defaultKey) {
		this.defaultKey = defaultKey;
	}

	public Properties getCodeDescProperties() {
		return codeDescProperties;
	}

	public void setCodeDescProperties(Properties codeDescProperties) {
		this.codeDescProperties = codeDescProperties;
	}

	public LicensePoolWarning getUserWarning(String warningCode) {
		return getUserWarning(warningCode, null);
	}

	public LicensePoolWarning getUserWarning(String warningCode, String message) {
		String code = null;
		String desc = null;

		if (warningCode == null) {
			code = defaultKey;
		} else {
			code = warningCode;
		}

		desc = codeDescProperties.getProperty(code);
		if (desc == null) {
			code = defaultKey;
			desc = codeDescProperties.getProperty(code);
		}

		return new LicensePoolWarning(message, new LicensePoolWarningElement(code, desc));
	}
}
