# Metadata keystore (brainstorming)
## Levels
```
<level1>.<level2>.<level3>.
```
```
Config Name				CONFIG VALUE
<level1>.<assetid>.<fileid>			12
<level1>.<assetid>.<fileid>.name		Geo Data
<level1>.<assetid>.<fileid>.format		{type=csv, colums=[a string, b string, c int]}
```
```
CONFIG NAME				CONFIG VALUE
owner.1.name				Neustar
owner.2.name				Verizon

assets.ids				    10,11

asset.10.name				CCPA Online
asset.10.fileids			20
fileid.20.name				Compliance
fileid.20.format			{type=csv, colums=[a string, b string, c int]}

asset.11.name				Gravy
asset.11.fileids			12,13,14
fileid.12.name				Geo Data
fileid.12.format			{type=csv, colums=[a string, b string, c int]}
fileid.13.name				People Data
fileid.13.format			{type=csv, colums=[a int, b int, c int]}
fileid.14.name				Things Data
fileid.14.format			{type=csv, colums=[a varchar, b boolean, c int]}
```

## REST API
* POST: userid (cert/user/password), assetid, fileid, version


