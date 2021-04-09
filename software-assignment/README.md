## Descripton

This program when run will start a webserver to interact with the CSV records and optionally take a csv file path to populate the records initially.

## Options

file-path: Full path to a csv file.

sort-method: Method of sorting to use. "last-name" "birth-date" "email"

## Examples

To prepopulate the webserver with an existing csv file.

lein run "resources/test.csv" "last-name"

To start the webserver without prepopulating.

lein run 

## BUGS

Will fail to parse windows line endings.
