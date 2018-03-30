
clear ; close all; clc

cd /home/singh/CsimSj/PreData/R1;


y = load('20.txt');
m = length(y); % number of training examples.
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
P1 = [1,21]*theta;


%%%%%%%%%%%%%%%%%%%

y = load('40.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
Q1 = [1,41]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('60.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
S1 = [1,61]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('100.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
T1 = [1,101]*theta;

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

cd /home/singh/CsimSj/PreData/R2;



y = load('20.txt');
m = length(y); % number of training examples.
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
P2 = [1,21]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('40.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
Q2 = [1,41]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('60.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
S2 = [1,61]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('100.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
T2 = [1,101]*theta;

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

cd /home/singh/CsimSj/PreData/R3;

y = load('20.txt');
m = length(y); % number of training examples.
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
P3 = [1,21]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('40.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
Q3 = [1,41]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('60.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
S3 = [1,61]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('100.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
T3 = [1,101]*theta;

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

cd /home/singh/CsimSj/PreData/R4;

y = load('20.txt');
m = length(y); % number of training examples.
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
P4 = [1,21]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('40.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
Q4 = [1,41]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('60.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
S4 = [1,61]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('100.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
T4 = [1,101]*theta;

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

cd /home/singh/CsimSj/PreData/R5;

y = load('20.txt');
m = length(y); % number of training examples.
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
P5 = [1,21]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('40.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
Q5 = [1,41]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('60.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
S5 = [1,61]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('100.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
T5 = [1,101]*theta;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

cd /home/singh/CsimSj/PreData/R6;

y = load('20.txt');
m = length(y); % number of training examples.
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
P6 = [1,21]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('40.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
Q6 = [1,41]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('60.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
S6 = [1,61]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('100.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
T6 = [1,101]*theta;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

cd /home/singh/CsimSj/PreData/R7;

y = load('20.txt');
m = length(y); % number of training examples.
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
P7 = [1,21]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('40.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
Q7 = [1,41]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('60.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
S7 = [1,61]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('100.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
T7 = [1,101]*theta;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

cd /home/singh/CsimSj/PreData/R8;

y = load('20.txt');
m = length(y); % number of training examples.
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
P8 = [1,21]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('40.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
Q8 = [1,41]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('60.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
S8 = [1,61]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('100.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
T8 = [1,101]*theta;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

cd /home/singh/CsimSj/PreData/R9;

y = load('20.txt');
m = length(y); % number of training examples.
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
P9 = [1,21]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('40.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
Q9 = [1,41]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('60.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
S9 = [1,61]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('100.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
T9 = [1,101]*theta;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

cd /home/singh/CsimSj/PreData/R10;

y = load('20.txt');
m = length(y); % number of training examples.
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
P10 = [1,21]*theta;


%%%%%%%%%%%%%%%%%%%

y = load('40.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
Q10 = [1,41]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('60.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
S10 = [1,61]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('100.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
T10 = [1,101]*theta;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

cd /home/singh/CsimSj/PreData/R11;

y = load('20.txt');
m = length(y); % number of training examples.
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
P11 = [1,21]*theta;


%%%%%%%%%%%%%%%%%%%

y = load('40.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
Q11 = [1,41]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('60.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
S11 = [1,61]*theta;Gurdas Maan : PUNJAB | Jatinder Shah | Gurickk G Maan | New Punjabi Songs 2017 | Saga Music 

%%%%%%%%%%%%%%%%%%%

y = load('100.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
T11 = [1,101]*theta;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

cd /home/singh/CsimSj/PreData/R12;

y = load('20.txt');
m = length(y); % number of training examples.
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
P12 = [1,21]*theta;


%%%%%%%%%%%%%%%%%%%

y = load('40.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
Q12 = [1,41]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('60.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
S12 = [1,61]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('100.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
T12 = [1,101]*theta;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

cd /home/singh/CsimSj/PreData/R13;

y = load('20.txt');
m = length(y); % number of training examples.
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
P13 = [1,21]*theta;


%%%%%%%%%%%%%%%%%%%

y = load('40.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
Q13 = [1,41]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('60.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
S13 = [1,61]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('100.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
T13 = [1,101]*theta;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

cd /home/singh/CsimSj/PreData/R14;

y = load('20.txt');
m = length(y); % number of training examples.
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
P14 = [1,21]*theta;


%%%%%%%%%%%%%%%%%%%

y = load('40.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
Q14 = [1,41]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('60.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
S14 = [1,61]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('100.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
T14 = [1,101]*theta;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

cd /home/singh/CsimSj/PreData/R15;

y = load('20.txt');
m = length(y); % number of training examples.
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
P15 = [1,21]*theta;


%%%%%%%%%%%%%%%%%%%

y = load('40.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
Q15 = [1,41]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('60.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
S15 = [1,61]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('100.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
T15 = [1,101]*theta;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

cd /home/singh/CsimSj/PreData/R16;

y = load('20.txt');
m = length(y); % number of training examples.
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
P16 = [1,21]*theta;


%%%%%%%%%%%%%%%%%%%

y = load('40.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
Q16 = [1,41]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('60.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
S16 = [1,61]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('100.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
T16 = [1,101]*theta;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

cd /home/singh/CsimSj/PreData/R17;

y = load('20.txt');
m = length(y); % number of training examples.
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
P17 = [1,21]*theta;


%%%%%%%%%%%%%%%%%%%

y = load('40.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
Q17 = [1,41]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('60.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
S17 = [1,61]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('100.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
T17 = [1,101]*theta;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

cd /home/singh/CsimSj/PreData/R18;

y = load('20.txt');
m = length(y); % number of training examples.
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
P18 = [1,21]*theta;


%%%%%%%%%%%%%%%%%%%

y = load('40.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
Q18 = [1,41]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('60.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
S18 = [1,61]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('100.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
T18 = [1,101]*theta;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

cd /home/singh/CsimSj/PreData/R19;

y = load('20.txt');
m = length(y); % number of training examples.
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
P19 = [1,21]*theta;


%%%%%%%%%%%%%%%%%%%

y = load('40.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
Q19 = [1,41]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('60.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
S19 = [1,61]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('100.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
T19 = [1,101]*theta;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

cd /home/singh/CsimSj/PreData/R20;

y = load('20.txt');
m = length(y); % number of training examples.
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
P20 = [1,21]*theta;


%%%%%%%%%%%%%%%%%%%

y = load('40.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
Q20 = [1,41]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('60.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
S20 = [1,61]*theta;

%%%%%%%%%%%%%%%%%%%

y = load('100.txt');
m = length(y); % number of training examples
x = [1:m];


X = [ones(m, 1), x']; % Add a column of ones to x

theta = pinv(X'*X)*X'*y; % calls normalEqn function 
T20 = [1,101]*theta;

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

PR6=[a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22, a23, a24, a25, a26, a27, a28, a29, a30, a31, a32, a33, a34, a35, a36, a37, a38, a39, a40, a41, a42, a43, a44, a45, a46, a47, a48, a49, a50]
PR11=[b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18, b19, b20, b21, b22, b23, b24, b25, b26, b27, b28, b29, b30, b31, b32, b33, b34, b35, b36, b37, b38, b39, b40, b41, b42, b43, b44, b45, b46, b47, b48, b49, b50]
PR21=[c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, c17, c18, c19, c20, c21, c22, c23, c24, c25, c26, c27, c28, c29, c30, c31, c32, c33, c34, c35, c36, c37, c38, c39, c40, c41, c42, c43, c44, c45, c46, c47, c48, c49, c50]
PR41=[d1, d2, d3, d4, d5, d6, d7, d8, d9, d10, d11, d12, d13, d14, d15, d16, d17, d18, d19, d20, d21, d22, d23, d24, d25, d26, d27, d28, d29, d30, d31, d32, d33, d34, d35, d36, d37, d38, d39, d40, d41, d42, d43, d44, d45, d46, d47, d48, d49, d50]
PR61=[e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14, e15, e16, e17, e18, e19, e20, e21, e22, e23, e24, e25, e26, e27, e28, e29, e30, e31, e32, e33, e34, e35, e36, e37, e38, e39, e40, e41, e42, e43, e44, e45, e46, e47, e48, e49, e50]
PR81=[f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, f13, f14, f15, f16, f17, f18, f19, f20, f21, f22, f23, f24, f25, f26, f27, f28, f29, f30, f31, f32, f33, f34, f35, f36, f37, f38, f39, f40, f41, f42, f43, f44, f45, f46, f47, f48, f49, f50]
PR101=[g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13, g14, g15, g16, g17, g18, g19, g20, g21, g22, g23, g24, g25, g26, g27, g28, g29, g30, g31, g32, g33, g34, g35, g36, g37, g38, g39, g40, g41, g42, g43, g44, g45, g46, g47, g48, g49, g50]



APR6 = sum(PR6)/50;
APR11 = sum(PR11)/50;
APR21 = sum(PR21)/50;
APR41 = sum(PR41)/50;
APR61 = sum(PR61)/50;
APR81 = sum(PR81)/50;
APR101 = sum(PR101)/50;

disp ("AVG OF 6 = "), disp (APR6);
disp ("AVG OF 11 = "), disp (APR11);
disp ("AVG OF 21 = "), disp (APR21);
disp ("AVG OF 41 = "), disp (APR41);
disp ("AVG OF 61 = "), disp (APR61);
disp ("AVG OF 81 = "), disp (APR81);
disp ("AVG OF 101 = "), disp (APR101);





