# jacoco-coverage-service

Converts jacoco.xml to json. Sample of resulting json:



```json
{
  "moduleCoverage": {
    "name": "Eloc",
    "packageCoverages": [
      {
        "name": "com.aurea.jacoco",
        "classCoverages": [
          {
            "name": "Duplicate",
            "methodCoverages": [
              {
                "name": "Duplicate(int, Range, int, String)",
                "instructionCovered": 0,
                "instructionUncovered": 15,
                "covered": 0,
                "uncovered": 6,
                "instructionsTotal": 15,
                "total": 6
              },
              {
                "name": "getTimes()",
                "instructionCovered": 0,
                "instructionUncovered": 3,
                "covered": 0,
                "uncovered": 1,
                "instructionsTotal": 3,
                "total": 1
              }
            ],
            "covered": 0,
            "uncovered": 12,
            "total": 12
          }
        ],
        "covered": 44,
        "uncovered": 146,
        "total": 190
      }
    ],
    "covered": 44,
    "uncovered": 146,
    "total": 190
  }
}
```

The name of each method coverage is built using: https://github.com/jacoco/jacoco/blob/34cd880f4e52a32b9f88ed4ea687b8f3f892395b/org.jacoco.report/src/org/jacoco/report/JavaNames.java

Curl example of request:

```
curl -X POST -H "Content-Type: multipart/form-data" -F "file=@jacoco.xml" https://jcc-coverage.herokuapp.com
```
