clear ; close all; clc

data = load('20');
X = data(:, 1); y = data(:, 2);
m = length(y); % number of training examples


X = [ones(m, 1), data(:,1)]; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
P = [1,21]*theta;

%%%%%%%%%%%%%%%%%%%

data = load('40');
X = data(:, 1); y = data(:, 2);
m = length(y); % number of training examples


X = [ones(m, 1), data(:,1)]; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
Q = [1,41]*theta;

%%%%%%%%%%%%%%%%%%%

data = load('60');
X = data(:, 1); y = data(:, 2);
m = length(y); % number of training examples


X = [ones(m, 1), data(:,1)]; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
S = [1,61]*theta;

%%%%%%%%%%%%%%%%%%%

data = load('100');
X = data(:, 1); y = data(:, 2);
m = length(y); % number of training examples


X = [ones(m, 1), data(:,1)]; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
T = [1,101]*theta;


