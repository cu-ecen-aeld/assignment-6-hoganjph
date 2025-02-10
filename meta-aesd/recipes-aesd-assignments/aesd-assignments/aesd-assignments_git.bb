inherit update-rc.d

# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-hoganjph.git;protocol=ssh;branch=master"

PV = "1.0+git${SRCPV}"
SRCREV = "956da68abf2306bf8e5ab49f8f32aa61f886d1c5"

# This sets your staging directory based on WORKDIR, where WORKDIR is defined at 
# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-WORKDIR
# We reference the "server" directory here to build from the "server" directory
# in your assignments repo
S = "${WORKDIR}/git/server"

FILES:${PN} += "${bindir}/aesdsocket"
FILES:${PN} += "/etc/init.d/S99aesdsocket"
TARGET_LDFLAGS += "-pthread -lrt"

# this is needed for installing start script
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesdsocket-start-stop"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install () {
        install -d ${D}${bindir}
        install -m 0755 ${S}/aesdsocket ${D}${bindir}/

        install -d ${D}${sysconfdir}/init.d
        install -m 0755 ${S}/aesdsocket-start-stop ${D}${sysconfdir}/init.d
}
