# tool

## to_csv.py

선택된 폴더에서 values*/strings.xml 들을 하나의 태이블로 합칩니다.

입력

```
res (입력된 폴더)
|-values
|--strings.xml
|-values-en
|--strings.xml
|-values-ko
|--strings.xml
|--strings_generated.xml (조건에 의해 제외)
|-value (조건에 의해 제외)
|--strings.xml
```

결과물

| id | values | values-en | values-ko |
| - | - | - | - |
| app_title | __app | __app | __앱 |

### 사용방법

1. $ `python3 res_to_csv.py input [resource path] output [output file]`


