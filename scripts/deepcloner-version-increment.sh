#!/bin/sh

# Script By Aashutos Kakshepati
#
# MIT LICENSE
#
# Copyright (c) 2018 92AK

# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documentation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
# copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:

# The above copyright notice and this permission notice shall be included in all
# copies or substantial portions of the Software.

# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
# SOFTWARE.
#

echo "Parameters :: VERSION STRING: $1; IS DRY RUN: $2; IS NON RELEASE TAG: $3"

# IF NOT A DRY RUN THEN CHANGES ARE COMMITTED (Push Changes can be set in pom, but by default it is pushed when it is not a dry run)
if [ "false" = $2 ]
then
  COMMIT="versions:commit"
  CMD_TAG="versions:set-scm-tag scm:tag scm:branch"
else
  COMMIT="versions:revert"
fi

# NOT A RELEASE TAG AND SO NO NEW BRANCH CREATED. IT IS TAGGED OF A BRANCH/VERSION BRANCH FOR FIXES (POSSIBLY FOR LTS VERSIONS etc.)
if [ "build-inc" = $3 ]
then
	TAG="$1"
  CMD_TAG="versions:set-scm-tag scm:tag"
else
	TAG="RELEASE-$1"
fi

if [ -z "$MAVEN_HOME" ]
then
  echo "MAVEN_HOME not set. Exiting..."
  exit 1
else
  echo "Executing Version bump Application..."
  $MAVEN_HOME/bin/mvn build-helper:parse-version versions:set $CMD_TAG $COMMIT -DnewVersion="$1" -DnewTag=$TAG -Dtag=$TAG -Dbranch=$TAG -Dmessage="[RELEASE] Pushing tag: $TAG" -f ../pom.xml
  echo "Execution complete."
fi
