# tool

## to_csv.py

입력

```
res
|-values
|--strings.xml
|-values-en
|--strings.xml
|-values-ko
|--strings.xml
```

결과물

| id | values | values-en | values-ko |
| - | - | - | - |
| app_title | __app | __app | __앱 |

### 사용방법

1. `% cd [resource 디렉토리]`
2. 현 디렉토리에 존재하는 대상 폴더를 아래 코드처럼 수정
```python
if __name__ == "__main__":
    create_csv([
        "values", "values-en",
        "values-ko", "values-ja",
        "values-it", ... ...
    ])
```
3. `% python3 to_csv.py`
4. `strings.csv` 파일 확인


