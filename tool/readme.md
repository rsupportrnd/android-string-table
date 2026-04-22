# tool

## merge.py

Merges same-named XML files found under the given folder into a single output file.

### Merge every `strings.xml` under a `res/` directory

`python merge.py input [res path]` or `python merge.py input [res path] target "strings.xml" output "merged_strings.xlsx"`

### Merge every `strings.xml` and `strings_generated.xml` under a `res/` directory

#### On duplicate ids, prefer `strings_generated.xml`

`python merge.py input [res path] target "strings.xml" target "strings_generated.xml" output "merged_strings.xlsx"`

#### On duplicate ids, prefer `strings.xml`

`python merge.py input [res path] target "strings_generated.xml" target "strings.xml" output "merged_strings.xlsx"`
