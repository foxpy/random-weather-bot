#!/bin/sh

# Allows executing from any directory without unexpected errors
cd "$(dirname "$0")"


repo_name="$(basename "$(pwd)")"

git_version="$(git rev-parse HEAD)"
git_version="${git_version:0:10}"


container_name="$repo_name-$git_version"
docker build -t "$container_name" .
