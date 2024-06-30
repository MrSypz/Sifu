#include <stdio.h>
#include <stdlib.h>
#include <string.h>

const char* getEntityJsonContent();
char* getGuiJsonContent(const char* itemName);
void writeToFile(const char* fileName, const char* content);

int main(void) {
    char itemName[100];
    printf("Enter your file name -> ");
    if (scanf("%99s", itemName) != 1) {
        fprintf(stderr, "Failed to read item name.\n");
        return 1;
    }

    char* entityJsonContent = getEntityJsonContent();
    char* guiJsonContent = getGuiJsonContent(itemName);

    char entityFileName[105];
    snprintf(entityFileName, sizeof(entityFileName), "%s.json", itemName);
    writeToFile(entityFileName, entityJsonContent);

    char guiFileName[109]; 
    snprintf(guiFileName, sizeof(guiFileName), "%s_gui.json", itemName);
    writeToFile(guiFileName, guiJsonContent);

    free(guiJsonContent);

    return 0;
}

const char* getEntityJsonContent() {
    return "{\n"
           "  \"parent\": \"builtin/entity\",\n"
           "  \"gui_light\": \"front\"\n"
           "}";
}

char* getGuiJsonContent(const char* itemName) {
    const char* baseJson = "{\n"
                           "  \"parent\": \"minecraft:item/generated\",\n"
                           "  \"textures\": {\n"
                           "    \"layer0\": \"sifu:item/%s\"\n"
                           "  }\n"
                           "}";
    size_t size = snprintf(NULL, 0, baseJson, itemName) + 1;
    char* jsonContent = malloc(size);
    if (jsonContent == NULL) {
        perror("Failed to allocate memory");
        exit(EXIT_FAILURE);
    }
    snprintf(jsonContent, size, baseJson, itemName);
    return jsonContent;
}

void writeToFile(const char* fileName, const char* content) {
    FILE* file = fopen(fileName, "w");
    if (file == NULL) {
        perror("Failed to open file for writing");
        exit(EXIT_FAILURE);
    }
    fprintf(file, "%s", content);
    fclose(file);
}
