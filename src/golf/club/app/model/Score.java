package golf.club.app.model;

public class Score {

	/*
	 * "scores": [
        {
            "@id": 1000000118,
            "@version": 1,
            "@status": "active",
            "@completionDate": "2013-12-17T00:00:00",
            "@shots": 222,
            "@netResult": 22,
            "golf": {
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
                "@distance": 0,
                "circuits": {
                    "circuit": []
                },
                "localizations": {
                    "localization": []
                }
            }
        },
	 */

	public String id,version,status,completionDate, shots,netResult,golf,localizations;

	public Score (String id1,String version1,String status1,String completionDate1, String shots1,String netResult1,String golf1,String localizations1){

		this.id=id1;
		this.version=version1;
		this.status=status1;
		this.completionDate=completionDate1;
		this.shots=shots1;
		this.netResult=netResult1;
		this.golf=golf1;
		this.localizations=localizations1;

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

	public String getCompletionDate() {
		return completionDate;
	}

	public String getShots() {
		return shots;
	}

	public String getNetResult() {
		return netResult;
	}

	public String getGolf() {
		return golf;
	}

	public String getLocalizations() {
		return localizations;
	}




}
