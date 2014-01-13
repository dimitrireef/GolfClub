package golf.club.app.model;

public class Chat {

	/*
	{
	    "messages": [
	        {
	            "@id": 1000000370,
	            "@version": 1,
	            "@content": "conseils, astuces pour ameliorer son swing",
	            "@sentDate": "2013-12-27T08:29:00",
	            "player": {
	                "@id": 1000000365,
	                "@version": 2,
	                "@nickName": "debra",
	                "@handicap": 0
	            }
	        },
	        {
	            "@id": 1000000371,
	            "@version": 1,
	            "@content": "Un bon set up est la combinaison d'une bonne posture, d'un bon alignement, d'un bon grip, d'un bon ̩quilibre, et plus encore. Posture: Le stance standard pour le swing",
	            "@sentDate": "2013-12-27T08:29:44",
	            "player": {
	                "@id": 1000000361,
	                "@version": 2,
	                "@nickName": "dexter",
	                "@handicap": 0
	            }
	        },
	        {
	            "@id": 1000000372,
	            "@version": 1,
	            "@content": "la descente : temporisez pour un swing harmonieux",
	            "@sentDate": "2013-12-27T08:30:02",
	            "player": {
	                "@id": 1000000361,
	                "@version": 2,
	                "@nickName": "dexter",
	                "@handicap": 0
	            }
	        },
	        {
	            "@id": 1000000373,
	            "@version": 1,
	            "@content": "Un genou droit dur comme la pierre...",
	            "@sentDate": "2013-12-27T08:30:51",
	            "player": {
	                "@id": 1000000365,
	                "@version": 2,
	                "@nickName": "debra",
	                "@handicap": 0
	            }
	        }
	    ]
	}

	 */

	public String id, version,content, sentDate, player;

	public Chat (String id,String version,String content,String sentDate,String player){

		this.id=id;
		this.version=version;
		this.content=content;
		this.sentDate=sentDate;
		this.player=player;

	}

	public String getId() {
		return id;
	}

	public String getVersion() {
		return version;
	}

	public String getContent() {
		return content;
	}

	public String getSentDate() {
		return sentDate;
	}

	public String getPlayer() {
		return player;
	}




}
