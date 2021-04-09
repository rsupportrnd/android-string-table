# android-string-table

## Introduction
- .xlsx 파일의 string 내용들을 res/values/strings_generated.xml 으로 변환한다.
- Language code 지원

## com.rsupport.download package
Download .xlsx from google spreadsheet

## com.ruspport.stringtable package 
Generate strings_generated.xml from .xlsx file

## com.rsupport.plugin packabe
Make project gradle plugin

## [tool](tool/readme.md)
Generate csv from string resource

# 언어 리소스 테이블화 툴
선택된 폴더 내부 strings.xml를 하나의 strings.csv파일로 합칩니다.

[더보기 링크](tool/readme.md)

## 방법
1. $ `python3 res_to_csv.py input [resource path] output [output file]`


# TODO
- [ ] Warn empty cell on "values" column - values 가 비어 있는데, 다른 곳에 값이 있을 경우
- [ ] 권한 축소 - 읽기 권한만
- [ ] 리소스 다시 생성 - strings_generated.xml 모두 제거하고 다시 생성하는 옵션 제공

## How to apply this plugin to my project?
- build.gradle(:project)
```c
buildscript {
    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
    dependencies {
        classpath 'com.github.seul-bi-keem:android-string-table:1.0.0.6'
    }
}
```

- build.gradle(:app)
```c
plugins {
    id 'rsupport-string-table'
}

stringResourceConfig {
    googleDriveCredentialPath [ credentials.json 파일의 경로 ]
    spreadSheetFieldId [ 해당 구글 시트 고유 id ]
    outputExcelFileName [ 생성될 엑셀 파일의 이름 ]
    outputResourcePath [ 생성될 xml파일의 경로 ]
    inputSheetName [ sheet 이름 ]
    indexRowNumber [ 인덱스 행이 몇번째 행인지 (0부터 시작) ]
}
```

- 구글 시트 고유 id ?
```c
예를 들어, 해당 구글 시트의 주소가
https://docs.google.com/spreadsheets/d/abcdef-123456789/edit#gid=12345
이면 고유 id는 abcdef-123456789 이다.