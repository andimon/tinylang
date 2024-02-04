%------------My Template Style For Latex-----------------
% author : Andre Vella
%--------------------------------------------------------

% Load book format 
\LoadClass[14pt,a4paper,oneside]{memoir}
% \def\logo{{\includegraphics[width=30mm]{umlogo_crest_red}}}
\setcounter{secnumdepth}{4}
\newcommand\AlCentroPagina[1]{%
\AddToShipoutPicture*{\AtPageCenter{%
\makebox(0,-50){\includegraphics[width=0.5\paperwidth]{#1}}}}}

\def\tagline#1{\gdef\@tagline{#1}}
\def\authorID#1{\gdef\@authorID{#1}}
\def\authorEMAIL#1{\gdef\@authorEMAIL{\href{mailto:#1}{#1}}}
\def\degree#1{\gdef\@degree{#1}}

%------------------------Colors--------------------------
%--------------------------------------------------------
%---------------------Start definitions------------------
\def\authorID#1{\gdef\@authorID{#1}}
\def\authorEMAIL#1{\gdef\@authorEMAIL{\href{mailto:#1}{#1}}}
%--------------------------------------------------------




%---------------------New Commands-----------------------

\renewcommand{\labelitemi}{$\textcolor{red}{\bullet}$}
\renewcommand{\labelitemii}{$\textcolor{red}{\cdot}$}
\renewcommand{\labelitemiii}{$\textcolor{red}{\diamond}$}
\renewcommand{\labelitemiv}{$\textcolor{red}{\ast}$}


%--------------------------------------------------------





%-----------------Add Required Packages------------------\
\RequirePackage[table,xcdraw]{xcolor}
\RequirePackage{amsfonts}
\RequirePackage{amssymb}
\RequirePackage{ifthen}
\RequirePackage{algorithm}
\RequirePackage{algpseudocode}
\RequirePackage{fvextra}
\RequirePackage{fontawesome5}
\RequirePackage{hyperref}
\RequirePackage{lscape}
\RequirePackage{listings}
\definecolor{codegreen}{rgb}{0,0.6,0}
\definecolor{codegray}{rgb}{0.5,0.5,0.5}
\definecolor{codepurple}{rgb}{0.58,0,0.82}
\definecolor{backcolour}{rgb}{0.95,0.95,0.92}
\lstdefinestyle{mystyle}{
    backgroundcolor=\color{backcolour},   
    commentstyle=\color{blue},
    keywordstyle=\color{red},
    numberstyle=\tiny\color{blue},
    stringstyle=\color{blue},
    basicstyle=\ttfamily\footnotesize,
    breakatwhitespace=false,         
    breaklines=true,                 
    captionpos=b,                    
    keepspaces=true,                 
    numbers=left,                    
    numbersep=5pt,                  
    showspaces=false,                
    showstringspaces=false,
    showtabs=false,                  
    tabsize=2
}
\lstset{style=mystyle}
\RequirePackage{amsmath}
\RequirePackage{enumerate}
\RequirePackage{float}
%package for drawing diagrams
\RequirePackage{tikz}
\usetikzlibrary{arrows, decorations.markings,decorations.pathreplacing,calligraphy,positioning, shapes.geometric}
% for double arrows a la chef
% adapt line thickness and line width, if needed
\tikzstyle{vecArrow} = [thick, decoration={markings,mark=at position
   1 with {\arrow[semithick]{open triangle 60}}},
   double distance=1.4pt, shorten >= 5.5pt,
   preaction = {decorate},
   postaction = {draw,line width=1.4pt, white,shorten >= 4.5pt}]
\tikzstyle{innerWhite} = [semithick, white,line width=1.4pt, shorten >= 4.5pt]
\RequirePackage{tcolorbox}
\RequirePackage{graphicx}
\RequirePackage{concmath}
\RequirePackage{titlesec, blindtext, color}
%\usepackage[default,oldstyle,scale=0.95]{opensans} %% Alternatively
%% use the option 'defaultsans' instead of 'default' to replace the
%% sans serif font only.
\RequirePackage{fontspec}
\RequirePackage[sfdefault]{carlito}
\RequirePackage{amsthm}
\newtheorem{theorem}{Theorem}
\newtheorem{lemma}{Lemma}
\newtheorem{definition}{Definitions}
\newtheorem{axiom}{Axiom}
\newtheorem{proposition}{Proposition}
\RequirePackage{caption}
\newfontfamily{\chapterfont}{title_font}[
Path = FontFiles/ ,
Scale=0.9,
Extension = .ttf,
]

\newfontfamily{\numberfont}{number_font}[
Path = FontFiles/ ,
Extension = .ttf
]

\newfontfamily{\titlefont}{title_font}[
Path = FontFiles/ ,
BoldFont =   *-bold,
BoldFeatures={Scale=1.2},
Extension = .ttf
]

%--------------------------------------------------------

%set chapter style

%set chapter style
\titleformat{\chapter}[hang]{\Huge\bfseries}{
% if chapter is 1 -> introduction
% if chapter is ... ->
{ \ifthenelse{\thechapter=0 \OR \thechapter=6}{\thechapter \;\textbf{|} \; }{Task \thechapter \;\textbf{|} \;}      }
}{0pt}{\Huge \color{red}}
%set section style %TODO
\titleformat{\section}[hang]{\Large\bfseries}{{ \thesection}\hsp\textbf{|} \hsp}{0pt}{\Large\color{blue}}

\titleformat{\subsection}[hang]{\Large\bfseries}{{ \thesubsection}\hsp\textbf{|} \hsp}{0pt}{\Large\color{blue}}

\titleformat{\subsubsection}[hang]{\large\bfseries}{{ \thesubsubsection}\hsp\textbf{|} \hsp}{0pt}{\large \color{blue}}
%set subsection style %TODO



\newenvironment{note}
    {\begin{tcolorbox}[colback=red!5!white,colframe=red!75!black,title= \textbf{Note:}]
    }
    { 
    \end{tcolorbox}
    }


%---------------Set abstract environment-----------------
\renewcommand{\abstractname}{\Huge\chapterfont\textcolor{red}{Abstract}}
\newcommand{\redB}[1]{\textcolor{red}{\textbf{#1}}}
\newcommand{\red}[1]{\textcolor{red}{#1}}
\newcommand{\blue}[1]{\textcolor{blue}{#1}}
\newcommand{\green}[1]{\textcolor{green}{#1}}
\newcommand{\brown}[1]{\textcolor{brown}{#1}}
\newcommand{\violet}[1]{\textcolor{violet}{#1}}
\newcommand{\gray}[1]{\textcolor{gray}{#1}}
\newcommand{\orange}[1]{\textcolor{orange}{#1}}



\newcommand{\ulq}{\mathopen{\textnormal{\textquotesingle}}}
\newcommand{\urq}{\mathclose{\textnormal{\textquotesingle}}}
\renewcommand*{\thepart}{\color{blue}\arabic{part}}
\renewcommand*{\parttitlefont}{\bfseries\color{red}\chapterfont\Large\MakeUppercase}
\renewcommand*{\partnamefont}{\color{blue}\chapterfont\Huge\MakeUppercase}%\normalfont\scshape\MakeLowercase}
\renewcommand*{\partnumfont}{\color{blue}}%\normalfont\scshape\MakeLowercase}
\newcommand{\hsp}{\hspace{20pt}}

%--------------------------------------------------------
\makeatletter
\newcommand{\github}[1]{%
   \href{#1}{\faGithubSquare}%
}
\makeatother



{
\titleformat{\paragraph}
{\normalfont\normalsize\bfseries}{\theparagraph}{1em}{}
\titlespacing*{\paragraph}
{0pt}{3.25ex plus 1ex minus .2ex}{1.5ex plus .2ex}
}

\makeatletter
\newcommand{\chapterkey}[1]{%
  \renewcommand{\chapter@key}{#1}%
}
\renewcommand{\maketitle}
{\begingroup

\parbox[b][0.95\textheight][t]{0.2\textwidth}{\raggedleft} %% this 0.95 is important, otherwise page overflows in next page
\hspace*{2ex}
\textcolor{red}{\rule{2pt}{0.95\textheight}}
\hspace*{2ex}
\parbox[b][0.95\textheight][t]{0.8\textwidth}{
    \setlength{\parindent}{0pt}
    %%\fontfamily{pag}\selectfont
    \sffamily
    {\Huge\bfseries{\begin{Spacing}{1.15}\textcolor{red}{\@title}\end{Spacing}}}
    \vspace*{2ex}
    {\textit{\@tagline}}\\[1in]
    
    {\LARGE\bfseries \textsc{\@author}} \\
    {\large \textsc{\@authorID}} \\
    {\large \textsc{\@authorEMAIL}} \\[1cm]
    

    %{\large Supervised by \@supervisor} 
    %\@ifundefined{@cosupervisor}{\\[2ex]}{\\[2ex]\large Co-supervised by \@cosupervisor}\\[1cm]
    %{\large \@department} \\[1ex]
    %{\large \@faculty} \\[1ex]
    {\large University of Malta} \\
    \vfill
    %{\bfseries \@degreedate}\\[\baselineskip]
	\parbox[t]{0.80\linewidth}{\footnotesize\textit{An assignment submitted in partial fulfilment of the requirements for the unit of \@degree}.}    
}
\thispagestyle{empty}
\if@openright\cleardoublepage\else\clearpage\fi
\endgroup}
