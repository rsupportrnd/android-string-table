# tool

## merge.py

선택된 폴더에서 선택된 동일 이름 xml파일들을 하나로 합칩니다.

### res 폴더 내 모든 strings.xml을 합치는 경우

`python merge.py input [res path]` or `python merge.py input [res path] target "strings.xml" output "merged_strings.xlsx"`

### res 폴더 내 모든 strings.xml strings_generated.xml을 합치는 경우

#### 중복 id 시, strings_generated.xml을 우선

`python merge.py input [res path] target "strings.xml" target "strings_generated.xml" output "merged_strings.xlsx"`

#### 중복 id 시 strings.xml을 우선

`python merge.py input [res path] target "strings_generated.xml" target "strings.xml" output "merged_strings.xlsx"`
