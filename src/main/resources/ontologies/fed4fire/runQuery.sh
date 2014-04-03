#!/usr/bin/env bash
# @author: Alexander Willner <alexander.willner@tu-berlin.de>
# @requirements:
#   - sparql command line tool (e.g. 'brew install jena')
#

function checkBinary {
  if command -v $1 >/dev/null 2>&1; then
     return 0
   else
     echo >&2 " * Checking for '$1'...FAILED."
     return 1
   fi
}

function checkEnvironment {
  _error=0
  checkBinary sparql; _error=$(($_error + $?))
#  checkBinary rapper; _error=$(($_error + $?))
  if [ "0" != "$_error" ]; then
    echo >&2 "FAILED. Please install the above mentioned binaries."
    exit 1
  fi
}

function runQuery {
  data="example-$1.ttl"
  query="query-$2.sparql"
  echo "Query:";
  echo "vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv"
  cat "${query}"
  echo "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"
  echo
  echo "Result:";
  echo "vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv"
  sparql --data="${data}" --query="${query}"
  echo "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"
}

checkEnvironment
[ "${#}" -eq 2 ] || { 
  echo "Usage: $(basename $0) <data> <query>"
  echo "Examples:"
  echo " - $(basename $0) request-vm getType"
  echo " - $(basename $0) advertisement-fp getnodes"
  exit 1;
}

runQuery $1 $2

#for arg in "$@"; do
#    [ "${arg}" = "getNodes" ] && runQuery advertisement-example-fp.ttl advertisement-query-getnodes.sparql
#    [ "${arg}" = "getNodesWithMon" ] && runQuery advertisement-example-fp.ttl advertisement-query-getnodes-with-mon.sparql
#    [ "${arg}" = "getCertificates" ] && runQuery advertisement-example-fp.ttl advertisement-query-getcertificate.sparql
#    [ "${arg}" = "getMetadata" ] && runQuery advertisement-example-fp.ttl advertisement-query-getcertificate.sparql
#done

