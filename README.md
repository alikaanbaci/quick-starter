# quick-starter
Quick starter web application with basic general requirements like routing, authentication etc.

## Basic Features
 - Authentication with JWT access & refresh token
 - User registration flow with email verification
 - Login, signup, landing pages
 - Generic layout

## Technology
 - Frontend: react, primereact, next.js
 - Backend: node.js, express.js, mongoose
 - Devops: github actions

## Devops
 - Build pipeline with github actions
 - Deploy to google cloud platform pipeline with github actions

## Development and Versioning
 - Development branch is development
 - When you fix a bug or implement new feature
	 1. Create a new branch from the issue
	 2. Make development and open a pr
	 3. Merge pr to development with rebase option
 - Release branch is main when you release a new version 
	 1. Merge development branch to main
	 2. Add a release tag
	 3. Deploy the release tag

