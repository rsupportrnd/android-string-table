# tool

## res_to_csv.py

선택된 폴더에서 선택된 동일 이름 xml파일들을 하나로 합칩니다.

### 예

path = ./res

target = strings.xml

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

1. $ `python3 res_to_csv.py input [resource path, 기본값 '.'] output [output file, 기본값 'strings.csv'] target [target xml, 기본값 'strings.xml']`

## merge.py

선택된 폴더에서 모든 `values*/strings.xml`, `values*/strings_generated.xml`을 하나로 합칩니다.

동일 아이디가 있는 경우 strings_generated.xml을 선택합니다.

### 사용방법

$ `python3 merge.py input [resource path, 기본값 '.'] output [output file, 기본값 'merged_strings.csv']`
