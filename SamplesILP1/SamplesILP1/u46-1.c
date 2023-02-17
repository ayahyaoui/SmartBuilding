#include <stdio.h> 
#include <stdlib.h> 
#include "ilp.h" 

/* Global variables */ 
ILP_Object print;


ILP_Object ilp_program () 
{ 
{ 
  ILP_Object ilptmp162; 
{ 
  ILP_Object ilptmp163; 
ilptmp163 =  ILP_String2ILP("Un, "); 
ilptmp162 = ILP_print(ilptmp163);
}
{ 
  ILP_Object ilptmp164; 
ilptmp164 =  ILP_String2ILP("deux et "); 
ilptmp162 = ILP_print(ilptmp164);
}
{ 
  ILP_Object ilptmp165; 
ilptmp165 =  ILP_String2ILP("trois."); 
ilptmp162 = ILP_print(ilptmp165);
}
return ilptmp162; 
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
