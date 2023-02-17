#include <stdio.h> 
#include <stdlib.h> 
#include "ilp.h" 

/* Global variables */ 


ILP_Object ilp_program () 
{ 
{ 
  ILP_Object ilptmp75; 
  ILP_Object ilptmp76; 
ilptmp75 = ILP_Integer2ILP(11); 
ilptmp76 = ILP_Integer2ILP(22); 

  {
    ILP_Object x1 = ilptmp75;
    ILP_Object y2 = ilptmp76;
{ 
  ILP_Object ilptmp77; 
  ILP_Object ilptmp78; 
{ 
  ILP_Object ilptmp79; 
  ILP_Object ilptmp80; 
ilptmp79 = x1; 
ilptmp80 = y2; 
ilptmp77 = ILP_Plus(ilptmp79, ilptmp80);
} 
{ 
  ILP_Object ilptmp81; 
  ILP_Object ilptmp82; 
ilptmp81 = x1; 
ilptmp82 = y2; 
ilptmp78 = ILP_Times(ilptmp81, ilptmp82);
} 

  {
    ILP_Object x3 = ilptmp77;
    ILP_Object y4 = ilptmp78;
{ 
  ILP_Object ilptmp83; 
  ILP_Object ilptmp84; 
ilptmp83 = x3; 
ilptmp84 = y4; 
return ILP_Times(ilptmp83, ilptmp84);
} 

  }
}

  }
}

} 

static ILP_Object ilp_caught_program () {
  struct ILP_catcher* current_catcher = ILP_current_catcher;
  struct ILP_catcher new_catcher;

  if ( 0 == setjmp(new_catcher._jmp_buf) ) {
    ILP_establish_catcher(&new_catcher);
    return ilp_program();
  };
  return ILP_current_exception;
}

int main (int argc, char *argv[]) 
{ 
  ILP_START_GC; 
  ILP_print(ilp_caught_program()); 
  ILP_newline(); 
  return EXIT_SUCCESS; 
} 
