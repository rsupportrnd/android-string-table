# android-string-table

## Introduction
- .xlsx 파일의 string 내용들을 res/values/strings_generated.xml 으로 변환한다.
- Language code 지원



## lib module 
Generate strings_generated.xml from .xlsx file

## download module 
Download .xlsx from google spreadsheet

## [tool](tool/readme.md)
Generate csv from string resource

# TODO
- [ ] Warn empty cell on "values" column - values 가 비어 있는데, 다른 곳에 값이 있을 경우
- [ ] 권한 축소 - 읽기 권한만
- [ ] 리소스 다시 생성 - strings_generated.xml 모두 제거하고 다시 생성하는 옵션 제공
