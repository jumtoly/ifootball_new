package com.ifootball.app.entity.release;

import java.io.Serializable;

public class ReleaseSelectAddress implements Serializable
{
	private static final long serialVersionUID = -8574358369591401895L;
	private String locationName;
	private String locationAddress;

	public ReleaseSelectAddress(String locationName, String locationAddress)
	{
		super();
		this.locationName = locationName;
		this.locationAddress = locationAddress;
	}

	public String getLocationName()
	{

		return locationName;
	}

	public void setLocationName(String locationName)
	{
		this.locationName = locationName;
	}

	public String getLocationAddress()
	{
		return locationAddress;
	}

	public void setLocationAddress(String locationAddress)
	{
		this.locationAddress = locationAddress;
	}

	// @Override
	// public int describeContents()
	// {
	// return 0;
	// }
	//
	// @Override
	// public void writeToParcel(Parcel dest, int flags)
	// {
	// dest.writeString(locationName);
	// dest.writeString(locationAddress);
	// }
	//
	// public static final Parcelable.Creator<ReleaseSelectAddress> CREATOR =
	// new Parcelable.Creator<ReleaseSelectAddress>()
	// {
	// public ReleaseSelectAddress createFromParcel(Parcel in)
	// {
	// return new ReleaseSelectAddress(in);
	// }
	//
	// public ReleaseSelectAddress[] newArray(int size)
	// {
	// return new ReleaseSelectAddress[size];
	// }
	// };
	//
	// private ReleaseSelectAddress(Parcel in)
	// {
	// locationName = in.readString();
	// locationAddress = in.readString();
	// }

}
