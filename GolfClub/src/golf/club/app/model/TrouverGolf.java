package golf.club.app.model;

public class TrouverGolf {

	/*
	 * {
    "golfs": {
        "golf": [
            {
                "@id": 1000000103,
                "@version": 1,
                "@status": "active",
                "@name": "The Mauritius Gymkhana's 18 Hole Golf Club",
                "@description": "The Mauritius Gymkhana Club was founded as a service club for British officers serving in Mauritius in the first half of the nineteenth century.",
                "@city": "Vacoas",
                "@phone": "+230 696-1404",
                "@email": "manager@mgc.mu",
                "@website": "www.mgc.intnet.mu",
                "@latitude": -20.2882,
                "@longitude": 57.499,
                "@defaultLocale": "",
                "@holes": 18,
                "@par": 72,
                "@slope": 0,
                "@distance": 5.005273,
                "circuits": {
                    "circuit": []
                },
                "localizations": {
                    "localization": []
                }
            },
            {
                "@id": 1000000105,
                "@version": 1,
                "@status": "active",
                "@name": "Trou aux Biches Hotel & Golf Club",
                "@description": "",
                "@city": "Triolet",
                "@phone": "+230 204-6565\t",
                "@email": "",
                "@website": "www.trouauxbiches-hotel.com",
                "@latitude": -20.0318,
                "@longitude": 57.548,
                "@defaultLocale": "",
                "@holes": 18,
                "@par": 32,
                "@slope": 0,
                "@distance": 24.341673,
                "circuits": {
                    "circuit": []
                },
                "localizations": {
                    "localization": []
                }
            }
        ]
    }
}
	 */

	public String id, version, status, name, description, city,phone,email,website,latitude,longitude,defaultLocale,holes, par,slope,distance,circuits,locatlizations;


	public TrouverGolf(String id1,String version1,String status1,String name1,String description1,String city1,String phone1,String email1,String website1,String latitude1,String longitude1,String defaultLocale1,String holes1, String par1,String slope1,String distance1,String circuits1,String locatlization1){

		this.id=id1;
		this.version=version1;
		this.status=status1;
		this.name=name1;
		this.description=description1;
		this.city=city1;
		this.phone=phone1;
		this.email=email1;
		this.website=website1;
		this.latitude=latitude1;
		this.longitude=longitude1;
		this.defaultLocale=defaultLocale1;
		this.holes=holes1;
		this.par=par1;
		this.slope=slope1;
		this.distance=distance1;
		this.circuits=circuits1;
		this.locatlizations=locatlization1;

	}


	public String getId() {
		return id;
	}


	public String getVersion() {
		return version;
	}


	public String getStatus() {
		return status;
	}


	public String getName() {
		return name;
	}


	public String getDescription() {
		return description;
	}


	public String getCity() {
		return city;
	}


	public String getPhone() {
		return phone;
	}


	public String getEmail() {
		return email;
	}


	public String getWebsite() {
		return website;
	}


	public String getLatitude() {
		return latitude;
	}


	public String getLongitude() {
		return longitude;
	}


	public String getDefaultLocale() {
		return defaultLocale;
	}


	public String getHoles() {
		return holes;
	}


	public String getPar() {
		return par;
	}


	public String getSlope() {
		return slope;
	}


	public String getDistance() {
		return distance;
	}


	public String getCircuits() {
		return circuits;
	}


	public String getLocatlizations() {
		return locatlizations;
	}


}
