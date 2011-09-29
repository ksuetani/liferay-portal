/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.social.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.social.model.SocialEquityAssetEntry;
import com.liferay.portlet.social.model.SocialEquityAssetEntryModel;

import java.io.Serializable;

import java.sql.Types;

/**
 * The base model implementation for the SocialEquityAssetEntry service. Represents a row in the &quot;SocialEquityAssetEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portlet.social.model.SocialEquityAssetEntryModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link SocialEquityAssetEntryImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialEquityAssetEntryImpl
 * @see com.liferay.portlet.social.model.SocialEquityAssetEntry
 * @see com.liferay.portlet.social.model.SocialEquityAssetEntryModel
 * @generated
 */
public class SocialEquityAssetEntryModelImpl extends BaseModelImpl<SocialEquityAssetEntry>
	implements SocialEquityAssetEntryModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a social equity asset entry model instance should use the {@link com.liferay.portlet.social.model.SocialEquityAssetEntry} interface instead.
	 */
	public static final String TABLE_NAME = "SocialEquityAssetEntry";
	public static final Object[][] TABLE_COLUMNS = {
			{ "equityAssetEntryId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "assetEntryId", Types.BIGINT },
			{ "informationK", Types.DOUBLE },
			{ "informationB", Types.DOUBLE }
		};
	public static final String TABLE_SQL_CREATE = "create table SocialEquityAssetEntry (equityAssetEntryId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,assetEntryId LONG,informationK DOUBLE,informationB DOUBLE)";
	public static final String TABLE_SQL_DROP = "drop table SocialEquityAssetEntry";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.social.model.SocialEquityAssetEntry"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.social.model.SocialEquityAssetEntry"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.portlet.social.model.SocialEquityAssetEntry"),
			true);
	public static long ASSETENTRYID_COLUMN_BITMASK = 1L;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.social.model.SocialEquityAssetEntry"));

	public SocialEquityAssetEntryModelImpl() {
	}

	public long getPrimaryKey() {
		return _equityAssetEntryId;
	}

	public void setPrimaryKey(long primaryKey) {
		setEquityAssetEntryId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_equityAssetEntryId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	public Class<?> getModelClass() {
		return SocialEquityAssetEntry.class;
	}

	public String getModelClassName() {
		return SocialEquityAssetEntry.class.getName();
	}

	public long getEquityAssetEntryId() {
		return _equityAssetEntryId;
	}

	public void setEquityAssetEntryId(long equityAssetEntryId) {
		_equityAssetEntryId = equityAssetEntryId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public long getAssetEntryId() {
		return _assetEntryId;
	}

	public void setAssetEntryId(long assetEntryId) {
		_columnBitmask |= ASSETENTRYID_COLUMN_BITMASK;

		if (!_setOriginalAssetEntryId) {
			_setOriginalAssetEntryId = true;

			_originalAssetEntryId = _assetEntryId;
		}

		_assetEntryId = assetEntryId;
	}

	public long getOriginalAssetEntryId() {
		return _originalAssetEntryId;
	}

	public double getInformationK() {
		return _informationK;
	}

	public void setInformationK(double informationK) {
		_informationK = informationK;
	}

	public double getInformationB() {
		return _informationB;
	}

	public void setInformationB(double informationB) {
		_informationB = informationB;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public SocialEquityAssetEntry toEscapedModel() {
		if (_escapedModelProxy == null) {
			_escapedModelProxy = (SocialEquityAssetEntry)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelProxyInterfaces,
					new AutoEscapeBeanHandler(this));
		}

		return _escapedModelProxy;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
					SocialEquityAssetEntry.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	@Override
	public Object clone() {
		SocialEquityAssetEntryImpl socialEquityAssetEntryImpl = new SocialEquityAssetEntryImpl();

		socialEquityAssetEntryImpl.setEquityAssetEntryId(getEquityAssetEntryId());
		socialEquityAssetEntryImpl.setGroupId(getGroupId());
		socialEquityAssetEntryImpl.setCompanyId(getCompanyId());
		socialEquityAssetEntryImpl.setUserId(getUserId());
		socialEquityAssetEntryImpl.setAssetEntryId(getAssetEntryId());
		socialEquityAssetEntryImpl.setInformationK(getInformationK());
		socialEquityAssetEntryImpl.setInformationB(getInformationB());

		socialEquityAssetEntryImpl.resetOriginalValues();

		return socialEquityAssetEntryImpl;
	}

	public int compareTo(SocialEquityAssetEntry socialEquityAssetEntry) {
		long primaryKey = socialEquityAssetEntry.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		SocialEquityAssetEntry socialEquityAssetEntry = null;

		try {
			socialEquityAssetEntry = (SocialEquityAssetEntry)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = socialEquityAssetEntry.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public void resetOriginalValues() {
		SocialEquityAssetEntryModelImpl socialEquityAssetEntryModelImpl = this;

		socialEquityAssetEntryModelImpl._originalAssetEntryId = socialEquityAssetEntryModelImpl._assetEntryId;

		socialEquityAssetEntryModelImpl._setOriginalAssetEntryId = false;

		socialEquityAssetEntryModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<SocialEquityAssetEntry> toCacheModel() {
		SocialEquityAssetEntryCacheModel socialEquityAssetEntryCacheModel = new SocialEquityAssetEntryCacheModel();

		socialEquityAssetEntryCacheModel.equityAssetEntryId = getEquityAssetEntryId();

		socialEquityAssetEntryCacheModel.groupId = getGroupId();

		socialEquityAssetEntryCacheModel.companyId = getCompanyId();

		socialEquityAssetEntryCacheModel.userId = getUserId();

		socialEquityAssetEntryCacheModel.assetEntryId = getAssetEntryId();

		socialEquityAssetEntryCacheModel.informationK = getInformationK();

		socialEquityAssetEntryCacheModel.informationB = getInformationB();

		return socialEquityAssetEntryCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{equityAssetEntryId=");
		sb.append(getEquityAssetEntryId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", assetEntryId=");
		sb.append(getAssetEntryId());
		sb.append(", informationK=");
		sb.append(getInformationK());
		sb.append(", informationB=");
		sb.append(getInformationB());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(25);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.social.model.SocialEquityAssetEntry");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>equityAssetEntryId</column-name><column-value><![CDATA[");
		sb.append(getEquityAssetEntryId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>assetEntryId</column-name><column-value><![CDATA[");
		sb.append(getAssetEntryId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>informationK</column-name><column-value><![CDATA[");
		sb.append(getInformationK());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>informationB</column-name><column-value><![CDATA[");
		sb.append(getInformationB());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = SocialEquityAssetEntry.class.getClassLoader();
	private static Class<?>[] _escapedModelProxyInterfaces = new Class[] {
			SocialEquityAssetEntry.class
		};
	private long _equityAssetEntryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private long _assetEntryId;
	private long _originalAssetEntryId;
	private boolean _setOriginalAssetEntryId;
	private double _informationK;
	private double _informationB;
	private transient ExpandoBridge _expandoBridge;
	private long _columnBitmask;
	private SocialEquityAssetEntry _escapedModelProxy;
}