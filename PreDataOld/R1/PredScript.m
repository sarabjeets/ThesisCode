clear ; close all; clc

y = load('20.txt');
m = length(y); % number of training examples.
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
P = [1,21]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('40.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
Q = [1,41]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('60.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
S = [1,61]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('100.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
T = [1,101]*theta;


