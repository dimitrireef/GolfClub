package golf.club.app.model;

public class MyCountry {



	/*
	 * Schema => {
    "countries": [
        {
            "@id": 1000000050,
            "@version": 1,
            "@status": "active",
            "@name": "France"
        },
        {
            "@id": 1000000053,
            "@version": 1,
            "@status": "active",
            "@name": "France"
        },
        {
            "@id": 1000000054,
            "@version": 1,
            "@status": "active",
            "@name": "France"
        }
    ]

    					country.add(new Country (name,status, version,id));

}
	 */


	public  String id, version,status, name;

	public MyCountry(String name1, String status1,String version1, String id1){

		this.name=name1;
		this.status=status1;
		this.version=version1;
		this.id=id1;


	}
}
