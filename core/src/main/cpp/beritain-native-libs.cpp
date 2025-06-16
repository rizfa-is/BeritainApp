#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_issog_core_utils_security_BeritainNativeLibs_beritainApiKey(JNIEnv *env, jobject thiz) {
    std::string value = "46d324d6f5ef44c38627e29bc47740d8";
    return env ->NewStringUTF(value.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_issog_core_utils_security_BeritainNativeLibs_beritainBaseUrl(JNIEnv *env, jobject thiz) {
    std::string value = "https://newsapi.org/v2/";
    return env ->NewStringUTF(value.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_issog_core_utils_security_BeritainNativeLibs_getNewsSourceUrl(JNIEnv *env, jobject thiz) {
    std::string value = "top-headlines/sources";
    return env ->NewStringUTF(value.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_issog_core_utils_security_BeritainNativeLibs_getTopHeadlineByCategoryUrl(JNIEnv *env, jobject thiz) {
    std::string value = "top-headlines";
    return env ->NewStringUTF(value.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_issog_core_utils_security_BeritainNativeLibs_beritainDb(JNIEnv *env, jobject thiz) {
    std::string value = "beritain.db";
    return env ->NewStringUTF(value.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_issog_core_utils_security_BeritainNativeLibs_beritainPassphrase(JNIEnv *env,jobject thiz) {
    std::string value = "DlyQ93bfKmYZ8Zy7BGt6tKELWtGmifc4";
    return env ->NewStringUTF(value.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_issog_core_utils_security_BeritainNativeLibs_beritainHostname(JNIEnv *env, jobject thiz) {
    std::string value = "newsapi.org";
    return env ->NewStringUTF(value.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_issog_core_utils_security_BeritainNativeLibs_beritainCertPinner1(JNIEnv *env,
                                                                          jobject thiz) {
    std::string value = "sha256/3k0pPb1Yzj4mQCfJt15zgP7MH7fBOtqfl6fZlHoNuLk";
    return env ->NewStringUTF(value.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_issog_core_utils_security_BeritainNativeLibs_beritainCertPinner2(JNIEnv *env,
                                                                          jobject thiz) {
    std::string value = "sha256/kIdp6NNEd8wsugYyyIYFsi1ylMCED3hZbSR8ZFsa/A4";
    return env ->NewStringUTF(value.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_issog_core_utils_security_BeritainNativeLibs_beritainCertPinner3(JNIEnv *env,
                                                                          jobject thiz) {
    std::string value = "sha256/mEflZT5enoR1FuXLgYYGqnVEoZvmf9c2bVBpiOjYQ0c";
    return env ->NewStringUTF(value.c_str());
}